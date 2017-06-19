package com.zxy.dao;

import java.util.List;
import java.util.Set;

import com.zxy.vo.Emp;
/**
 * ����emp�������ݲ�Ĳ�����׼
 * @author Zhangxy
 *
 */
public interface IEmpDAO {
	/**
	 * ʵ�����ݵ����Ӳ���
	 * @param vo ������Ҫ���ӵ����ݵ�VO����
	 * @return ���ݱ���ɹ�����true�����򷵻�false
	 * @throws Exception SQLִ���쳣
	 */
	public boolean doCreate(Emp vo) throws Exception;
	/**
	 * ʵ�����ݵ��޸Ĳ����������޸��Ǹ���id����ȫ���ֶ����ݵ��޸�
	 * @param vo ������Ҫ�޸ĵ����ݵ���Ϣ��һ��Ҫ�ṩ��ID����
	 * @return �����޸ĳɹ�����true�����򷵻�false
	 * @throws Exception SQLִ���쳣
	 */
	public boolean doUpdate(Emp vo) throws Exception;
	/**
	 * ִ�����ݵ�����ɾ������������Ҫɾ����������Set���ϵ���ʽ����
	 * @param ids ������Ҫɾ��������ID���������ظ�����
	 * @return ɾ���ɹ�����true��ɾ�������ݸ�����Ҫɾ�������ݸ�����ͬ�������򷵻�false
	 * @throws Exception SQLִ���쳣
	 */
	public boolean doRemoveBatch(Set<Integer> ids) throws Exception;
	/**
	 * ���ݹ�Ա��Ų�ѯָ���Ĺ�Ա��Ϣ
	 * @param id Ҫ��ѯ�Ĺ�Ա��Ϣ
	 * @return �����Ա��Ϣ���ڣ���������VO��������ʽ���أ������Ա��Ϣ�����ڣ��򷵻�null
	 * @throws Exception SQLִ���쳣
	 */
	public Emp findById(Integer id) throws Exception;
	/**
	 * ��ѯָ�����ݱ���ȫ����¼�������Լ��ϵ���ʽ����
	 * @return ������������ݣ������е����ݻ��װΪVO�����������List���Ϸ��أ�
	 * ���û�����ݣ���ô���ϵĳ���Ϊ0��size() == 0������null��
	 * @throws Exception SQLִ���쳣
	 */
	public List<Emp> findAll() throws Exception;
	/**
	 * ��ҳ�������ݵ�ģ����ѯ����ѯ����Լ��ϵ���ʽ����
	 * @param currentPage ��ǰ���ڵ�ҳ
	 * @param lineSize ÿҳ��ʾ����������
	 * @param column Ҫ����ģ����ѯ��������
	 * @param keyword ģ����ѯ�Ĺؼ���
	 * @return ������������ݣ������е����ݻ��װΪVO�����������List���Ϸ��أ�
	 * ���û�����ݣ���ô���ϵĳ���Ϊ0��size() == 0������null��
	 * @throws Exception SQLִ���쳣
	 */
	public List<Emp> findAllSpilt(Integer currentPage, Integer lineSize, String column, String keyword) throws Exception;
	/**
	 * ����ģ����ѯ����������ͳ�ƣ��������û�м�¼ͳ�ƵĽ������0
	 * @param column Ҫ����ģ����ѯ��������
	 * @param keyword ģ����ѯ�Ĺؼ���
	 * @return ���ر��е������������û�����ݷ���0
	 * @throws Exception SQLִ���쳣
	 */
	public Integer getAllCount(String column, String keyword) throws Exception;
}