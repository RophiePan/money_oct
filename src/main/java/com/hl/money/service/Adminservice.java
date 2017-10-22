package com.hl.money.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hl.money.entity.Admin;
import com.hl.money.entity.Bonus;
import com.hl.money.entity.User;
import com.hl.money.entity.UserNode;
import com.hl.money.enums.BonusStatus;
import com.hl.money.enums.BonusType;
import com.hl.money.enums.SubBranch;
import com.hl.money.enums.UserStatus;
import com.hl.money.exception.MyException;
import com.hl.money.repository.AdminRepository;
import com.hl.money.repository.BonusRepository;
import com.hl.money.repository.UserNodeRepository;
import com.hl.money.repository.UserRepository;

@Service
public class Adminservice {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserNodeRepository userNodeRepository;

	@Autowired
	private UserNodeService userNodeService;

	@Autowired
	private BonusRepository bonusRepository;

	public Admin getUserByIdPassword(final Admin admin) {
		final Example<Admin> example = Example.of(admin);
		return this.adminRepository.findOne(example);
	}

	@Transactional
	public void auditUserPass(final Integer userId) {
		final User user = this.userRepository.getOne(userId);

		// 推荐人拿30%的提成
		final int recommendId = user.getRecommendId();
		UserNode recommendUserNode = null;
		final List<UserNode> recommendUserNodeList = this.userNodeService.getUserNodeByUserId(recommendId);
		if (0 == recommendUserNodeList.size()) {
			throw new MyException("推荐人系统未建点");
		} else {
			recommendUserNode = recommendUserNodeList.get(0);
		}
		final List<UserNode> userNodeList = this.userNodeService.getUserNodeByUserId(userId);
		if (userNodeList.size() > 0) {
			throw new MyException("此人已建点！");
		}

		// 取出所有节点
		final List<UserNode> allNode = this.userNodeRepository.findAll();

		final UserNode newNode = new UserNode();
		newNode.setUserId(userId);
		newNode.setUserName(user.getUserName());
		newNode.setCreateDate(new Date());
		newNode.setRootNodeId(recommendId);

		final int size = this.getChildrenSize(allNode, recommendId);
		// 如果推荐人下面没有结点，则推荐人直接作为ParentId来建点
		if (size == 0) {
			newNode.setParentId(recommendId);
			newNode.setParentName(user.getRecommendName());
			newNode.setSubBranch(SubBranch.LEFT.getValue());
			newNode.setLevel(recommendUserNode.getLevel() + 1);

			this.userNodeRepository.save(recommendUserNode);
		} else {
			final UserNode parentNode = this.findNodeByRecommendId(allNode, recommendUserNode, recommendUserNode);
			if (this.getChildrenSize(allNode, parentNode.getUserId()) == 0) {
				newNode.setSubBranch(SubBranch.LEFT.getValue());
			} else if (this.getChildrenSize(allNode, parentNode.getUserId()) == 1) {
				newNode.setSubBranch(SubBranch.RIGHT.getValue());
			} else {
				throw new MyException("错误的节点");
			}
			newNode.setLevel(parentNode.getLevel() + 1);
			newNode.setParentId(parentNode.getUserId());
			newNode.setParentName(parentNode.getUserName());

			this.userNodeRepository.save(parentNode);
		}
		this.userNodeRepository.save(newNode);

		final List<Bonus> bonusList = new ArrayList<>();
		// 插入奖金
		final Bonus recommendBonus = new Bonus();
		recommendBonus.setUserId(recommendId);
		recommendBonus.setAmount(1500);
		recommendBonus.setBonusType(BonusType.RECOMMEND.toString());
		recommendBonus.setAwardDate(new Date());
		recommendBonus.setComments("推荐用户：" + user.getUserName() + "-" + newNode.getUserId() + " 奖金");
		recommendBonus.setStatus(BonusStatus.UNDELIVER.getValue());
		bonusList.add(recommendBonus);

		final List<UserNode> parentNodes = new ArrayList<>();
		this.getParentNode(allNode, newNode.getParentId(), parentNodes);

		for (final UserNode userNode : parentNodes) {
			final Bonus nodeBonus = new Bonus();
			nodeBonus.setAmount(50);
			nodeBonus.setAwardDate(new Date());
			nodeBonus.setBonusType(BonusType.CREATENODE.toString());
			nodeBonus.setUserId(userNode.getUserId());
			nodeBonus.setComments("用户：" + user.getUserName() + "-" + newNode.getUserId() + "  建点奖金");
			nodeBonus.setStatus(BonusStatus.UNDELIVER.getValue());
			bonusList.add(nodeBonus);
		}
		this.bonusRepository.save(bonusList);

		// 用户审核通过
		user.setUserStatus(UserStatus.AUDITED.getValue());
		user.setAuditDate(new Date());
		this.userRepository.save(user);

	}

	private void getParentNode(final List<UserNode> allNode, final int userId, final List<UserNode> parentNodes) {
		for (final UserNode userNode : allNode) {
			if ((userNode.getUserId() == userId) && (userNode.getUserId() != 0)) {
				parentNodes.add(userNode);
				this.getParentNode(allNode, userNode.getParentId(), parentNodes);
			}
		}
	}

	private int getChildrenSize(final List<UserNode> allNode, final int userId) {
		final List<UserNode> childrenNode = new ArrayList<>();
		for (final UserNode userNode : allNode) {
			if ((userNode.getParentId() == userId) && (userNode.getLevel() != 1)) {
				childrenNode.add(userNode);
			}
		}
		return childrenNode.size();
	}

	/**
	 * 
	 * @param allNode
	 * @param recommendUserNode
	 * @param originNode
	 *            一直作为最初的推荐node，如果推荐人左右全满，则此节点会变更为左右分支下的点
	 * @return
	 */
	private UserNode findNodeByRecommendId(final List<UserNode> allNode, final UserNode recommendUserNode,
			final UserNode originNode) {

		final List<UserNode> childNodes = new ArrayList<>();
		for (final UserNode userNode : allNode) {
			if (userNode.getParentId().intValue() == recommendUserNode.getUserId().intValue()
					&& userNode.getUserId().intValue() != 10000) {
				childNodes.add(userNode);
			}
		}

		final List<UserNode> originChildNodes = new ArrayList<>();
		for (UserNode userNode : allNode) {
			if (userNode.getParentId().intValue() == originNode.getUserId().intValue()
					&& userNode.getUserId().intValue() != 10000) {
				originChildNodes.add(userNode);
			}
		}

		if (childNodes.size() == 0) {
			// 如果节点在第八层，则小于等于7，可以放在此节点下面，作为最后一层第9层
			if ((recommendUserNode.getLevel() - originNode.getLevel()) <= 7) {
				return recommendUserNode;
			}
			// 如果为第九层，则返回自己，在右侧开分支----此判断是用来判断左侧满点的
			else {
				// 如果推荐人已经有两个节点，并且右侧已满，刚从子节点重新开始查找
				if (this.getChildrenSize(allNode, originNode.getUserId()) == 2) {
					// 从左侧找
					return this.findNodeByRecommendId(allNode, originChildNodes.get(0), originChildNodes.get(0));
				} else {
					return originNode;
				}
			}
		} else if (childNodes.size() == 1) {
			// 如果子节点为1，则继续判断此节点下面有没有人，所以需要把recomendUserNode，换成子节点的Node
			return this.findNodeByRecommendId(allNode, childNodes.get(0), originNode);
		} else {
			for (final UserNode userNode : childNodes) {
				if (userNode.getSubBranch() == SubBranch.RIGHT.getValue()) {
					// 右侧继续往下寻找
					return this.findNodeByRecommendId(allNode, userNode, originNode);
				}
			}
		}
		return null;
	}

}
