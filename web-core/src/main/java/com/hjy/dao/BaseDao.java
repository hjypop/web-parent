package com.hjy.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Flush;

 
/**
 * @descriptions 底层基本的dao的接口
 * 
 * @param <T> 对应的实体类
 * @param <Pk> primary key，即数据库自增ID 
 * @date 2016年5月19日下午3:00:17
 * @author Yangcl
 * @version 1.0.1
 */
public interface BaseDao<T, PK extends Serializable> {

	/**
	 * @descriptions 保存单一对象，如果实体中的某个字段为null则不保存这个字段
	 * 	这种保存方法更加灵活。
	 *  
	 * @param entity
	 * @return
	 * @refactor no
	 * @date 2016年5月19日下午3:20:23
	 * @author Yangcl
	 * @version 1.0.0.1
	 */
    public Integer insertSelective(T entity); 
    
    /**
     * @descriptions 保存单一对象，并返回这条记录的生成自增id 
     * 
     * @param entity
     * @return
     * @refactor no
     * @date 2016年5月19日下午3:24:06
     * @author Yangcl
     * @version 1.0.0.1
     */
    public Integer insertGotEntityId(T entity); 
    
    /**
     * @descriptions 保存单一对象，并返回这条记录生成的uuid 
     * 
     * @param entity
     * @return
     * @refactor no
     * @date 2016年5月19日下午3:26:47
     * @author Yangcl
     * @version 1.0.0.1
     */
    public Integer insertGotEntityUuid(T entity); 
    
    /**
     * @descriptions 批量添加操作，保存一个对象集合
     * 
     * @param list
     * @return
     * @refactor no
     * @date 2016年5月19日下午3:28:51
     * @author Yangcl
     * @version 1.0.0.1 
     */
    public Integer batchInsert(List<T> list); 
    
    
 
    /**
     * @descriptions 更新对象,如果属性中的主键(id)为空，则不会进行对应的属性值更新。
     * 	如果实体中的某个字段为null则不更新这个字段。这种更新方法更加灵活。
     * 
     * @param entity
     * @return
     * @refactor no
     * @date 2016年5月19日下午3:33:22
     * @author Yangcl
     * @version 1.0.0.1
     */
    public Integer updateSelective(T entity); 
    
    /**
     * @descriptions 批量更新操作。
     * 	注意！此方法需要你自己在*****Mapper.xml 文件中写批量更新脚本
     * 
     * @param list
     * @return
     * @refactor no
     * @date 2016年5月19日下午3:37:07
     * @author Yangcl
     * @version 1.0.0.1
     */
    public Integer batchUpdate(List<T> list); 
    
    
    
    /**
     * @descriptions 根据id删除对象 
     * 
     * @param id
     * @return
     * @refactor no
     * @date 2016年5月19日下午3:41:49
     * @author Yangcl
     * @version 1.0.0.1
     */
    public Integer deleteById(PK id); 
    
    /**
     * @descriptions 根据id list 删除对象集合|删除多条记录
     * 	注意！此方法需要你自己在*****Mapper.xml 文件中写批量删除脚本
     * 
     * @param list
     * @return
     * @refactor no
     * @date 2016年5月19日下午3:42:23
     * @author Yangcl
     * @version 1.0.0.1
     */
    public Integer batchDelete(List<Integer> list);
    
    /**
     * @descriptions 根据条件集合删除对象
     * 	注意！此方法需要你自己在*****Mapper.xml 文件中写批量删除脚本
     * 
     * @param dto 数据传输对象(Data Transfer Object)   mybatis进行对象匹配的属性
     * @refactor no
     * @date 2016年5月19日下午3:45:00
     * @author Yangcl
     * @version 1.0.0.1
     */
    public <DTO> void deleteByCondition(DTO dto); 
  
    
    
    /**
     * @descriptions 根据id进行对象查询
     * 
     * @param id
     * @return
     * @refactor no
     * @date 2016年5月19日下午3:56:41
     * @author Yangcl
     * @version 1.0.0.1
     */
    public T find(PK id); 
    
    /**
     * @descriptions 根据任意属性查询 
     * 	注意！此方法需要你自己在*****Mapper.xml 文件中重写查询条件
     * 
     * @param entity 包含查询条件的实体
     * @return List<T>
     * @refactor no
     * @date 2016年5月19日下午4:00:11
     * @author Yangcl
     * @version 1.0.0.1
     */
    @Flush
    public List<T> findList(T entity); 
    
    
    /**
     * @descriptions 根据数据传输对象中的条件进行查询。此方法常用于分组查询：group by 
     * 	Dto 不同于Entity，但他们都是实体类。
     * 	注意！此方法需要你自己在*****Mapper.xml 文件中重写脚本
     * 
     * @param dto  数据传输对象(Data Transfer Object) 
     * @return
     * @refactor no
     * @date 2016年5月19日下午4:10:52
     * @author Yangcl
     * @version 1.0.0.1
     */
    public <DTO> List<T> findGroupList(DTO dto);  
    
    /**
     * @descriptions 根据条件进行数量的查询
     * 	注意！此方法需要你自己在*****Mapper.xml 文件中重写查询条件
     * 
     * @param entity 包含查询条件的实体
     * @return 返回符合条件的泛型参数对应表中的数量
     * @refactor no
     * @date 2016年5月19日下午4:18:57
     * @author Yangcl
     * @version 1.0.0.1
     */
    public int count(T entity); 
    
    /**
     * @descriptions 根据条件集合进行分页查询
     * 	注意！此方法需要你自己在*****Mapper.xml 文件中重写查询条件
     * 
     * @param entity
     * @return
     * @refactor no
     * @date 2016年5月19日下午4:21:53
     * @author Yangcl
     * @version 1.0.0.1
     */
    public List<T> queryPage(T entity);  
    
    /**
     * @descriptions 根据任意属性进行模糊查询
     * 	注意！此方法需要你自己在*****Mapper.xml 文件中重写查询条件
     * @param entity
     * @return
     * @refactor no
     * @date 2016年5月19日下午4:23:24
     * @author Yangcl
     * @version 1.0.0.1
     */
    public List<T> like(T entity); 
    
    /**
     * @descriptions 查询实体对应表最大Id.该方法只有在主键为int时才有用
     * 	注意！此方法 可能 需要你自己在*****Mapper.xml 文件中重写查询条件
     * 
     * @exception/throws 如果主键类型不为int，会抛出类型转换异常 
     * @return 返回泛型参数对应表的主键最大值
     * @refactor no
     * @date 2016年5月19日下午4:24:36
     * @author Yangcl
     * @version 1.0.0.1
     */
    public Integer selectMaxId(); 
    
}








