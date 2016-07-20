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
	final String[] WEEK={"星期馹","星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
	public QuestionData(){
		pd=new PersonData();
		cal=Calendar.getInstance();//使用日历类
		year=cal.get(Calendar.YEAR);//得到年
		month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
		day=cal.get(Calendar.DAY_OF_MONTH);//得到天
		week=cal.get(Calendar.DAY_OF_WEEK);
		monthList=getRand(month,12);
		dayList=getRand(day,31);
		weekList=getRand(week,7);
	}
	
	public String getQuesData(int i){
		switch(i){
		case 0:
			return "现在是哪一年？";       //答案年份不会变化
		case 1:
			return "现在是什么季节？";
		case 2:
			return "现在是几月?";
		case 3:
			return "现在是几号?";
		case 4:
			return "今天是星期几?";
		case 5:
			return "咱们现在是在哪个国家?";
		case 6:
			return "咱们现在是在哪个城市?";
		case 7:
			return "咱们现在是在哪个城区?";
		case 8:
			return "现在在哪个展厅?";
		case 9:
			return "现在在第几层楼?";
		case 10:
			return "我告诉你三种东西,我说完后请你重复一边这三种东西是什么。并请你记住，" +
					"过一会儿还要让你回忆出它们的名字.此题由志愿者打分，语音将5秒后播放";
		case 12:
			return "13.	现在请你说出刚才让你记住的那三样东西,此题由志愿者打分.";
		}
		return null;
	}
	
	public String getChoiceData1(int i){
		switch(i){
		case 0:
			return "1. 2013";
		case 1:
			return "1. 春季";
		case 2:
			return "1. "+monthList[0]+"月";
		case 3:
			return "1. "+dayList[0]+"号";
		case 4:
			return "1. "+WEEK[weekList[0]];
		case 5:
			return "1. 中国";
		case 6:
			return "1. 浙江";
		case 7:
			return "1. 宝山区";
		case 8:
			return "1. 展厅1";
		case 9:
			return "1. 第一层";
		case 10:
		case 12:
			return "1. 记忆错误";
			
		}
		return null;
	}
	
	public String getChoiceData2(int i){
		switch(i){
		case 0:
			return "2. 2014";
		case 1:
			return "2. 夏季";
		case 2:
			return "2. "+monthList[1]+"月";
		case 3:
			return "2. "+dayList[1]+"号";
		case 4:
			return "2. "+WEEK[weekList[1]];
		case 5:
			return "2. 日本";
		case 6:
			return "2. 北京";
		case 7:
			return "2. 虹口区";
		case 8:
			return "2. 展厅2";
		case 9:
			return "2. 第二层";
		case 10:
		case 12:
			return "2. 记住一样";
		}
		return null;
	}

	public String getChoiceData3(int i){
		switch(i){
		case 0:
			return "3. 2015";
		case 1:
			return "3. 秋季";
		case 2:
			return "3. "+monthList[2]+"月";
		case 3:
			return "3. "+dayList[2]+"号";
		case 4:
			return "3. "+WEEK[weekList[2]];
		case 5:
			return "3. 美国";
		case 6:
			return "3. 上海";
		case 7:
			return "3. 杨浦区";
		case 8:
			return "3. 展厅3";
		case 9:
			return "3. 第三层";
		case 10:
		case 12:
			return "3. 记住两样";
		}
		return null;
	}

	public String getChoiceData4(int i){
		switch(i){
		case 0:
			return "4. 2016";
		case 1:
			return "4. 冬季";
		case 2:
			return "4. "+monthList[3]+"月";
		case 3:
			return "4. "+dayList[3]+"号";
		case 4:
			return "4. "+WEEK[weekList[3]];
		case 5:
			return "4. 英国";
		case 6:
			return "4. 江苏";
		case 7:
			return "4. 闸北区";
		case 8:
			return "4. 展厅4";
		case 9:
			return "4. 第四层";
		case 10:
		case 12:
			return "4. 记住三样";
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
			//12 1 2冬/   3 4 5春/  6 7 8夏/  9 10 11 秋
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
