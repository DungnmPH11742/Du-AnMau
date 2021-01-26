package DAO;

import java.util.List;

abstract public class EduSysDAO<EntityType, KeyType> {

    //EntityType lớp đối tượng
    //KeyType key truyền vào
    public EduSysDAO() {
    }

    //Phương thức trìu tượng
    abstract public void insertCSDL(EntityType entity);

    abstract public void updateCSDL(EntityType entity);

    abstract public void deleteCSDL(KeyType key);

    abstract public List<EntityType> loadDataFull();

    abstract public List<EntityType> loadDataID(EntityType entity);
    
    abstract public List<EntityType> loadDataKey(String key);

}
