package com.zxy.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zxy.vo.Emp;
/**
 * ����emp���ҵ����ִ�б�׼������һ��Ҫ�������ݿ�Ĵ���رղ���
 * �������ͨ��DAOFactory��ȡ��IEmpDAO�Ľӿڶ���
 * @author Zhangxy
 *
 */
public interface IEmpService {
	/**
	 * ʵ�ֹ�Ա���ݵ����Ӳ��������β���Ҫ����IEmpDAO�ӿڵ����·�����
	 * 1����Ҫ����IEmpDAO.findById()�������ж�Ҫ���ӵ�id�Ƿ��Ѿ����ڣ�
	 * 2���������Ҫ���ӵ����ݱ�Ų����������IEmpDAO.daCreate���������ز����Ľ��
	 * @param vo ����Ҫ�������ݵ�VO����
	 * @return ����������ݵ�ID�ظ����߱���ʧ�ܷ���false�����򷵻�true
	 * @throws Exception SQLִ���쳣
	 */
	public boolean insert(Emp vo) throws Exception;
	/**
	 * ʵ�ֹ�Ա���ݵ��޸Ĳ���������Ҫ����IEmpDAO.doUpdate()�����������޸�����ȫ�����ݵ��޸�
	 * @param vo ����Ҫ�޸����ݵ�VO����
	 * @return �޸ĳɹ�����true�����򷵻�false
	 * @throws Exception SQLִ���쳣
	 */
	public boolean update(Emp vo) throws Exception;
	/**
	 * ִ�й�Ա���ݵ�ɾ������������ɾ�������Ա��Ϣ������IEmpDAO.doRemoveBatch()����
	 * @param ids ������Ҫɾ�����ݵļ��ϣ��������ظ�����
	 * @return ɾ���ɹ�����true�����򷵻�false
	 * @throws Exception SQLִ���쳣
	 */
	public boolean delete(Set<Integer> ids) throws Exception;
	/**
	 * ���ݹ�Ա��Ų��ҵĹ�Ա������Ϣ������IEmpDAO.findById()����
	 * @param id Ҫ���ҵĹ�Ա���
	 * @return ����ҵ������Ա��Ϣ��VO���󷵻أ����򷵻�null
	 * @throws Exception SQLִ���쳣
	 */
	public Emp get(Integer id) throws Exception;
	/**
	 * ��ѯȫ����Ա��Ϣ������IEmpDAO.findAll()����
	 * @return ��ѯ�����List���ϵ���ʽ���أ����û�������򼯺ϵĳ���Ϊ0
	 * @throws Exception SQLִ���쳣
	 */
	public List<Emp> list() throws Exception;
	/**
	 * ʵ�����ݵ�ģ����ѯ������ͳ�ƣ�Ҫ����IEmpDAO�ӿڵ�����������
	 * 1������IEmpDAO.findAllSpilt()��������ѯ�����еı�ṹ�����ص�List<Emp>;
	 * 2������IEmpDAO.getAllCount()��������ѯ���е������������ص�Integer��
	 * @param currentPage ��ǰ����ҳ
	 * @param lineSize ÿҳ��ʾ�ļ�¼��
	 * @param column ģ����ѯ��������
	 * @param keyword ģ����ѯ�ؼ���
	 * @return ������������Ҫ���ض����������ͣ�����ʹ��Map���Ϸ��أ��������Ͳ�ͳһ����������value����������ΪObject�������������£�
	 * 1��key = allEmps��value = IEmpDAO.findAllSpilt()���ؽ����List<Emp>
	 * 2��key = empCount��value = IEmpDAO.getAllCount()���ؽ����Integer
	 * @throws Exception SQLִ���쳣
	 */
	public Map<String, Object> list(int currentPage, int lineSize, String column, String keyword) throws Exception;
}
