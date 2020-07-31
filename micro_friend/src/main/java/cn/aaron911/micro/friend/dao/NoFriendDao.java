package cn.aaron911.micro.friend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.aaron911.micro.friend.pojo.NoFriend;

public interface NoFriendDao extends JpaRepository<NoFriend, String> {
	
    public NoFriend findByUseridAndFriendid(String userid, String friendid);
}
