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
		if(userNodeList.size()>0){
			throw new MyException("此人已建点！");
		}

		// 取出所有节点
		final List<UserNode> allNode = this.userNodeRepository.findAll();

		final UserNode newNode = new UserNode();
		newNode.setUserId(userId);
		newNode.setCreateDate(new Date());
		newNode.setRootNodeId(recommendId);

		final int size = this.getChildrenSize(allNode, recommendId);
		// 如果推荐人下面没有结点，则推荐人直接作为ParentId来建点
		if (size == 0) {
			newNode.setParentId(recommendId);
			newNode.setSubBranch(SubBranch.LEFT.getValue());
			newNode.setLevel(recommendUserNode.getLevel() + 1);
		} else if (size == 1) {
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
		}
		this.userNodeRepository.save(newNode);

		final List<Bonus> bonusList = new ArrayList<>();
		// 插入奖金
		final Bonus recommendBonus = new Bonus();
		recommendBonus.setUserId(recommendId);
		recommendBonus.setAmount(1500);
		recommendBonus.setBonusType(BonusType.RECOMMEND.toString());
		recommendBonus.setAwardDate(new Date());
		recommendBonus.setComments("推荐 " + newNode.getUserId() + " 奖金");
		bonusList.add(recommendBonus);

		final List<UserNode> parentNodes = new ArrayList<>();
		this.getParentNode(allNode, newNode.getParentId(), parentNodes);

		for (final UserNode userNode : parentNodes) {
			final Bonus nodeBonus = new Bonus();
			nodeBonus.setAmount(50);
			nodeBonus.setAwardDate(new Date());
			nodeBonus.setBonusType(BonusType.CREATENODE.toString());
			nodeBonus.setUserId(userNode.getUserId());
			nodeBonus.setComments(newNode.getUserId() + "  建点奖金");
			bonusList.add(nodeBonus);
		}
		this.bonusRepository.save(bonusList);

		// 用户审核通过
		user.setUserStatus(UserStatus.AUDITED.getValue());
		this.userRepository.save(user);

	}

	private void getParentNode(final List<UserNode> allNode, final int userId, final List<UserNode> parentNodes) {
		for (final UserNode userNode : allNode) {
			if ((userNode.getUserId() == userId) && (userNode.getUserId() != 10000)) {
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

	private UserNode findNodeByRecommendId(final List<UserNode> allNode, final UserNode recommendUserNode,
			final UserNode childNode) {

		final List<UserNode> childNodes = new ArrayList<>();
		for (final UserNode userNode : allNode) {
			if (userNode.getParentId() == recommendUserNode.getUserId()) {
				childNodes.add(userNode);
			}
		}
		if (childNodes.size() == 0) {
			// 如果节点在第八层，则小于等于7，可以放在此节点下面，作为最后一层第9层
			if ((childNode.getLevel() - recommendUserNode.getLevel()) <= 7) {
				return childNode;
			}
			// 如果为第九层，则返回自己，在右侧开分支
			else {
				// 如果推荐人已经有两个节点，并且右侧已满，刚从子节点重新开始查找
				if (childNodes.size() == 2) {
					final int i = Math.random() > 0.5d ? 0 : 1;
					this.findNodeByRecommendId(allNode, childNodes.get(i), childNodes.get(i));
				} else {
					return recommendUserNode;
				}
			}
		} else if (childNodes.size() == 1) {
			this.findNodeByRecommendId(allNode, recommendUserNode, childNodes.get(0));
		} else {
			for (final UserNode userNode : childNodes) {
				if (userNode.getSubBranch() == SubBranch.RIGHT.getValue()) {
					this.findNodeByRecommendId(allNode, recommendUserNode, userNode);
				}
			}
		}
		return null;
	}

}
