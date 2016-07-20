package com.example.mmset.data;

import java.util.Calendar;
import java.util.Random;

public class QuestionData {

	/**
	 * @param args
	 */
	PersonData pd;
	static int grade;
	Calendar cal;
	int year,month,day,week;
	int[] monthList,dayList,weekList;
	final String[] WEEK={"�����_","����һ","���ڶ�","������","������","������","������","������"};
	public QuestionData(){
		pd=new PersonData();
		cal=Calendar.getInstance();//ʹ��������
		year=cal.get(Calendar.YEAR);//�õ���
		month=cal.get(Calendar.MONTH)+1;//�õ��£���Ϊ��0��ʼ�ģ�����Ҫ��1
		day=cal.get(Calendar.DAY_OF_MONTH);//�õ���
		week=cal.get(Calendar.DAY_OF_WEEK);
		monthList=getRand(month,12);
		dayList=getRand(day,31);
		weekList=getRand(week,7);
	}
	
	public String getQuesData(int i){
		switch(i){
		case 0:
			return "��������һ�ꣿ";       //����ݲ���仯
		case 1:
			return "������ʲô���ڣ�";
		case 2:
			return "�����Ǽ���?";
		case 3:
			return "�����Ǽ���?";
		case 4:
			return "���������ڼ�?";
		case 5:
			return "�������������ĸ�����?";
		case 6:
			return "�������������ĸ�����?";
		case 7:
			return "�������������ĸ�����?";
		case 8:
			return "�������ĸ�չ��?";
		case 9:
			return "�����ڵڼ���¥?";
		case 10:
			return "�Ҹ��������ֶ���,��˵��������ظ�һ�������ֶ�����ʲô���������ס��" +
					"��һ�����Ҫ�����������ǵ�����.������־Ը�ߴ�֣�������5��󲥷�";
		case 12:
			return "13.	��������˵���ղ������ס������������,������־Ը�ߴ��.";
		}
		return null;
	}
	
	public String getChoiceData1(int i){
		switch(i){
		case 0:
			return "1. 2013";
		case 1:
			return "1. ����";
		case 2:
			return "1. "+monthList[0]+"��";
		case 3:
			return "1. "+dayList[0]+"��";
		case 4:
			return "1. "+WEEK[weekList[0]];
		case 5:
			return "1. �й�";
		case 6:
			return "1. �㽭";
		case 7:
			return "1. ��ɽ��";
		case 8:
			return "1. չ��1";
		case 9:
			return "1. ��һ��";
		case 10:
		case 12:
			return "1. �������";
			
		}
		return null;
	}
	
	public String getChoiceData2(int i){
		switch(i){
		case 0:
			return "2. 2014";
		case 1:
			return "2. �ļ�";
		case 2:
			return "2. "+monthList[1]+"��";
		case 3:
			return "2. "+dayList[1]+"��";
		case 4:
			return "2. "+WEEK[weekList[1]];
		case 5:
			return "2. �ձ�";
		case 6:
			return "2. ����";
		case 7:
			return "2. �����";
		case 8:
			return "2. չ��2";
		case 9:
			return "2. �ڶ���";
		case 10:
		case 12:
			return "2. ��סһ��";
		}
		return null;
	}

	public String getChoiceData3(int i){
		switch(i){
		case 0:
			return "3. 2015";
		case 1:
			return "3. �＾";
		case 2:
			return "3. "+monthList[2]+"��";
		case 3:
			return "3. "+dayList[2]+"��";
		case 4:
			return "3. "+WEEK[weekList[2]];
		case 5:
			return "3. ����";
		case 6:
			return "3. �Ϻ�";
		case 7:
			return "3. ������";
		case 8:
			return "3. չ��3";
		case 9:
			return "3. ������";
		case 10:
		case 12:
			return "3. ��ס����";
		}
		return null;
	}

	public String getChoiceData4(int i){
		switch(i){
		case 0:
			return "4. 2016";
		case 1:
			return "4. ����";
		case 2:
			return "4. "+monthList[3]+"��";
		case 3:
			return "4. "+dayList[3]+"��";
		case 4:
			return "4. "+WEEK[weekList[3]];
		case 5:
			return "4. Ӣ��";
		case 6:
			return "4. ����";
		case 7:
			return "4. բ����";
		case 8:
			return "4. չ��4";
		case 9:
			return "4. ���Ĳ�";
		case 10:
		case 12:
			return "4. ��ס����";
		}
		return null;
	}
	
	public void answerMatch(int quesID,int choice){
		switch(quesID){
		case 0:
			if(choice==1){
				pd.save(1);
				grade++;
			}
			else pd.save(0);
			break;
		case 1:
			//12 1 2��/   3 4 5��/  6 7 8��/  9 10 11 ��
			if(choice%4==(month%12/3)){
				pd.save(1);
				grade++;
			}
			else pd.save(0);
			break;
		case 2:
			if(monthList[choice-1]==month){
				pd.save(1);
				grade++;
			}
			else pd.save(0);
			break;
		case 3:
			if(dayList[choice-1]==day){
				pd.save(1);
				grade++;
			}
			else pd.save(0);
			break;
		case 4:
			if(weekList[choice-1]==7)
				if(week==0){
					pd.save(1);
					grade++;
				}
				else pd.save(0);
			else if(weekList[choice-1]==week)
			{
				pd.save(1);
				grade++;
			}
			else
				pd.save(0);
			break;
		case 5:
			if(choice==1){
				pd.save(1);
				grade++;
			}
			else pd.save(0);
			break;
		case 6:
			if(choice==3){
				pd.save(1);
				grade++;
			}
			else pd.save(0);
			break;
		case 7:
			if(choice==1){
				pd.save(1);
				grade++;
			}
			else pd.save(0);
			break;
		case 8:
			if(choice==4){
				pd.save(1);
				grade++;
			}
			else pd.save(0);
			break;
		case 9:
			if(choice==2){
				pd.save(1);
				grade++;
			}
			else pd.save(0);
			break;
		case 10:
		case 12:
			pd.save(choice-1);
			grade=grade+choice-1;
			break;
		case 13:
			if(choice==2){
				pd.save(1);
				grade++;
			}
			else pd.save(0);
			break;
		case 14:
			if(choice==1){
				pd.save(1);
				grade++;
			}
			else pd.save(0);
			break;
		case 15:
			if(choice==2){
				pd.save(1);
				grade++;
			}
			else pd.save(0);
			break;
		case 16:
			pd.save(choice-1);
			grade=grade+choice-1;
			break;
		case 17:
		case 18:
		case 19:
			if(choice==1){
				pd.save(1);
				grade++;
			}
			else pd.save(0);
			break;
		}
	}
	
	public void answerMatch(int quesID,String ans1,String ans2,String ans3,String ans4,String ans5){
		int i=0;
		switch(quesID){
		case 11:
			if(ans1.equals("91"))
				i++;
			if(ans2.equals("76"))
				i++;
			if(ans3.equals("59"))
				i++;
			if(ans4.equals("38"))
				i++;
			if(ans5.equals("30"))
				i++;
			pd.save(i);
			grade=grade+i;
			break;
		}
	}
	
	public int getGrade(){
		return grade;
	}
	public void setGrade(){
		grade=0;
	}
	
	private int[] getRand(int number,int max){
		Random rand=new Random();
		int count=0;
		int[] a=new int[4];
		int[] result=new int[4];
		boolean flag=true;
		int b=0;
		while(count<3){
			flag=true;
			b=Math.abs(rand.nextInt()%4);
			for(int i=0;i<count+1;i++){
				if(b==a[i])
					flag=false;
			}
			if(flag){
				a[count]=b;
				count++;
			}
			
		}
		for(int i=0;i<4;i++){
			result[i]=number+a[i]-2;
			if(result[i]<1)
				result[i]=number+a[i]-2+max;
			if(result[i]>max)
				result[i]=1;
		}
		return result;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
