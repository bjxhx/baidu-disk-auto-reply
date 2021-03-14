package xyz.xhx.dao;

import org.springframework.stereotype.Repository;
import xyz.xhx.entity.AutoKeyCommons;

import java.util.List;
import java.util.Map;

@Repository
public interface DuyunDao {

    Map<String,Object> getUserInfo(String baidu_uk);

    void saveUser(Map<String, String> map);

    List<AutoKeyCommons> findAllAutoKey();

    void deleteAutoKeyById(int id);

    void addAutoKey(AutoKeyCommons autoKey);

    void editAutoKey(AutoKeyCommons autoKey);
}
