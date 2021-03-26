package com.huihe.eg.user.model.count;
import com.cy.framework.util.CommonUtils;
import com.cy.framework.util.FinalConfigParam;

import java.util.Date;

/**@author  作者yangjing: 折线统计图参数
 * @date 创建时间：2016-12-14 上午11:26:44 
 */
public class ChartParam {
	private int yiwNum;
	private int erNum;
	private int saNum;
	private int siNum;
	private int wuNum;
	private int liuNum;
	private int qiNum;
	private Date yi;
	private String yiStr;
	private Date er;
	private String erStr;
	private Date sa;
	private String saStr;
	private Date si;
	private String siStr;
	private Date wu;
	private String wuStr;
	private Date liu;
	private String liuStr;
	private Date qi;
	private String qiStr;
	
	private int eightNum;
	private int nineNum;
	private int tenNum;
	private int elevenNum;
	private int twelveNum;
	private int thirteenNum;
	private int forteenNum;
	private int fifteenNum;
	private int sixteenNum;
	private int seventeenNum;
	private int eighteenNum;
	private int nineteenNum;
	private int twentyNum;
	private int twentyOneNum;
	private int twentyTwoNum;
	private int twentyThreeNum;
	private int twentyFourNum;
	private int twentyFiveNum;
	private int twentySixNum;
	private int twentySevenNum;
	private int twentyEightNum;
	private int twentyNineNum;
	private int thirtyNum;
	
	private Date eight;
	private Date nine;
	private Date ten;
	private Date eleven;
	private Date twelve;
	private Date thirteen;
	private Date forteen;
	private Date fifteen;
	private Date sixteen;
	private Date seventeen;
	private Date eighteen;
	private Date nineteen;
	private Date twenty;
	private Date twentyOne;
	private Date twentyTwo;
	private Date twentyThree;
	private Date twentyFour;
	private Date twentyFive;
	private Date twentySix;
	private Date twentySeven;
	private Date twentyEight;
	private Date twentyNine;
	private Date thirty;
	private String eightStr;
	private String nineStr;
	private String tenStr;
	private String elevenStr;
	private String twelveStr;
	private String thirteenStr;
	private String forteenStr;
	private String fifteenStr;
	private String sixteenStr;
	private String seventeenStr;
	private String eighteenStr;
	private String nineteenStr;
	private String twentyStr;
	private String twentyOneStr;
	private String twentyTwoStr;
	private String twentyThreeStr;
	private String twentyFourStr;
	private String twentyFiveStr;
	private String twentySixStr;
	private String twentySevenStr;
	private String twentyEightStr;
	private String twentyNineStr;
	private String thirtyStr;
	
	//月份
	private int january;
	private int february;
	private int march;
	private int april;
	private int may;
	private int june;
	private int july;
	private int august;
	private int september;
	private int october;
	private int november;
	private int december;

	//会员等级
    private Integer member_level;
    //学习卡类型
	private String study_type;
	//
	private String rcharge_type;

	public String getRcharge_type() {
		return rcharge_type;
	}

	public void setRcharge_type(String rcharge_type) {
		this.rcharge_type = rcharge_type;
	}

	public String getStudy_type() {
		return study_type;
	}
	public void setStudy_type(String study_type) {
		this.study_type = study_type;
	}
	public Integer getMember_level() {
		return member_level;
	}
	public void setMember_level(Integer member_level) {
		this.member_level = member_level;
	}
	public int getEightNum() {
		return eightNum;
	}
	public void setEightNum(int eightNum) {
		this.eightNum = eightNum;
	}
	public int getNineNum() {
		return nineNum;
	}
	public void setNineNum(int nineNum) {
		this.nineNum = nineNum;
	}
	public int getTenNum() {
		return tenNum;
	}
	public void setTenNum(int tenNum) {
		this.tenNum = tenNum;
	}
	public int getElevenNum() {
		return elevenNum;
	}
	public void setElevenNum(int elevenNum) {
		this.elevenNum = elevenNum;
	}
	public int getTwelveNum() {
		return twelveNum;
	}
	public void setTwelveNum(int twelveNum) {
		this.twelveNum = twelveNum;
	}
	public int getThirteenNum() {
		return thirteenNum;
	}
	public void setThirteenNum(int thirteenNum) {
		this.thirteenNum = thirteenNum;
	}
	public int getForteenNum() {
		return forteenNum;
	}
	public void setForteenNum(int forteenNum) {
		this.forteenNum = forteenNum;
	}
	public int getFifteenNum() {
		return fifteenNum;
	}
	public void setFifteenNum(int fifteenNum) {
		this.fifteenNum = fifteenNum;
	}
	public int getSixteenNum() {
		return sixteenNum;
	}
	public void setSixteenNum(int sixteenNum) {
		this.sixteenNum = sixteenNum;
	}
	public int getSeventeenNum() {
		return seventeenNum;
	}
	public void setSeventeenNum(int seventeenNum) {
		this.seventeenNum = seventeenNum;
	}
	public int getEighteenNum() {
		return eighteenNum;
	}
	public void setEighteenNum(int eighteenNum) {
		this.eighteenNum = eighteenNum;
	}
	public int getNineteenNum() {
		return nineteenNum;
	}
	public void setNineteenNum(int nineteenNum) {
		this.nineteenNum = nineteenNum;
	}
	public int getTwentyNum() {
		return twentyNum;
	}
	public void setTwentyNum(int twentyNum) {
		this.twentyNum = twentyNum;
	}
	public int getTwentyOneNum() {
		return twentyOneNum;
	}
	public void setTwentyOneNum(int twentyOneNum) {
		this.twentyOneNum = twentyOneNum;
	}
	public int getTwentyTwoNum() {
		return twentyTwoNum;
	}
	public void setTwentyTwoNum(int twentyTwoNum) {
		this.twentyTwoNum = twentyTwoNum;
	}
	public int getTwentyThreeNum() {
		return twentyThreeNum;
	}
	public void setTwentyThreeNum(int twentyThreeNum) {
		this.twentyThreeNum = twentyThreeNum;
	}
	public int getTwentyFourNum() {
		return twentyFourNum;
	}
	public void setTwentyFourNum(int twentyFourNum) {
		this.twentyFourNum = twentyFourNum;
	}
	public int getTwentyFiveNum() {
		return twentyFiveNum;
	}
	public void setTwentyFiveNum(int twentyFiveNum) {
		this.twentyFiveNum = twentyFiveNum;
	}
	public int getTwentySixNum() {
		return twentySixNum;
	}
	public void setTwentySixNum(int twentySixNum) {
		this.twentySixNum = twentySixNum;
	}
	public int getTwentySevenNum() {
		return twentySevenNum;
	}
	public void setTwentySevenNum(int twentySevenNum) {
		this.twentySevenNum = twentySevenNum;
	}
	public int getTwentyEightNum() {
		return twentyEightNum;
	}
	public void setTwentyEightNum(int twentyEightNum) {
		this.twentyEightNum = twentyEightNum;
	}
	public int getTwentyNineNum() {
		return twentyNineNum;
	}
	public void setTwentyNineNum(int twentyNineNum) {
		this.twentyNineNum = twentyNineNum;
	}
	public int getThirtyNum() {
		return thirtyNum;
	}
	public void setThirtyNum(int thirtyNum) {
		this.thirtyNum = thirtyNum;
	}
	
	
	
	
	public Date getEight() {
		return eight;
	}
	public void setEight(Date eight) {
		this.eightStr=CommonUtils.date2string(eight, FinalConfigParam.DATE_FORMAT_STYLE);
		this.eight = eight;
	}
	public Date getNine() {
		return nine;
	}
	public void setNine(Date nine) {
		this.nineStr=CommonUtils.date2string(nine, FinalConfigParam.DATE_FORMAT_STYLE);
		this.nine = nine;
	}
	public Date getTen() {
		return ten;
	}
	public void setTen(Date ten) {
		this.tenStr=CommonUtils.date2string(ten, FinalConfigParam.DATE_FORMAT_STYLE);
		this.ten = ten;
	}
	public Date getEleven() {
		return eleven;
	}
	public void setEleven(Date eleven) {
		this.elevenStr=CommonUtils.date2string(eleven, FinalConfigParam.DATE_FORMAT_STYLE);
		this.eleven = eleven;
	}
	public Date getTwelve() {
		return twelve;
	}
	public void setTwelve(Date twelve) {
		this.twelveStr=CommonUtils.date2string(twelve, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twelve = twelve;
	}
	public Date getThirteen() {
		return thirteen;
	}
	public void setThirteen(Date thirteen) {
		this.thirteenStr=CommonUtils.date2string(thirteen, FinalConfigParam.DATE_FORMAT_STYLE);
		this.thirteen = thirteen;
	}
	public Date getForteen() {
		return forteen;
	}
	public void setForteen(Date forteen) {
		this.forteenStr=CommonUtils.date2string(forteen, FinalConfigParam.DATE_FORMAT_STYLE);
		this.forteen = forteen;
	}
	public Date getFifteen() {
		return fifteen;
	}
	public void setFifteen(Date fifteen) {
		this.fifteenStr=CommonUtils.date2string(fifteen, FinalConfigParam.DATE_FORMAT_STYLE);
		this.fifteen = fifteen;
	}
	public Date getSixteen() {
		return sixteen;
	}
	public void setSixteen(Date sixteen) {
		this.sixteenStr=CommonUtils.date2string(sixteen, FinalConfigParam.DATE_FORMAT_STYLE);
		this.sixteen = sixteen;
	}
	public Date getSeventeen() {
		return seventeen;
	}
	public void setSeventeen(Date seventeen) {
		this.seventeenStr=CommonUtils.date2string(seventeen, FinalConfigParam.DATE_FORMAT_STYLE);
		this.seventeen = seventeen;
	}
	public Date getEighteen() {
		return eighteen;
	}
	public void setEighteen(Date eighteen) {
		this.eighteenStr=CommonUtils.date2string(eighteen, FinalConfigParam.DATE_FORMAT_STYLE);
		this.eighteen = eighteen;
	}
	public Date getNineteen() {
		return nineteen;
	}
	public void setNineteen(Date nineteen) {
		this.nineteenStr=CommonUtils.date2string(nineteen, FinalConfigParam.DATE_FORMAT_STYLE);
		this.nineteen = nineteen;
	}
	public Date getTwenty() {
		return twenty;
	}
	public void setTwenty(Date twenty) {
		this.twentyStr=CommonUtils.date2string(twenty, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twenty = twenty;
	}
	public Date getTwentyOne() {
		return twentyOne;
	}
	public void setTwentyOne(Date twentyOne) {
		this.twentyOneStr=CommonUtils.date2string(twentyOne, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twentyOne = twentyOne;
	}
	public Date getTwentyTwo() {
		return twentyTwo;
	}
	public void setTwentyTwo(Date twentyTwo) {
		this.twentyTwoStr=CommonUtils.date2string(twentyTwo, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twentyTwo = twentyTwo;
	}
	public Date getTwentyThree() {
		return twentyThree;
	}
	public void setTwentyThree(Date twentyThree) {
		this.twentyThreeStr=CommonUtils.date2string(twentyThree, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twentyThree = twentyThree;
	}
	public Date getTwentyFour() {
		return twentyFour;
	}
	public void setTwentyFour(Date twentyFour) {
		this.twentyFourStr=CommonUtils.date2string(twentyFour, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twentyFour = twentyFour;
	}
	public Date getTwentyFive() {
		return twentyFive;
	}
	public void setTwentyFive(Date twentyFive) {
		this.twentyFiveStr=CommonUtils.date2string(twentyFive, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twentyFive = twentyFive;
	}
	public Date getTwentySix() {
		return twentySix;
	}
	public void setTwentySix(Date twentySix) {
		this.twentySixStr=CommonUtils.date2string(twentySix, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twentySix = twentySix;
	}
	public Date getTwentySeven() {
		return twentySeven;
	}
	public void setTwentySeven(Date twentySeven) {
		this.twentySevenStr=CommonUtils.date2string(twentySeven, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twentySeven = twentySeven;
	}
	public Date getTwentyEight() {
		return twentyEight;
	}
	public void setTwentyEight(Date twentyEight) {
		this.twentyEightStr=CommonUtils.date2string(twentyEight, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twentyEight = twentyEight;
	}
	public Date getTwentyNine() {
		return twentyNine;
	}
	public void setTwentyNine(Date twentyNine) {
		this.twentyNineStr=CommonUtils.date2string(twentyNine, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twentyNine = twentyNine;
	}
	public Date getThirty() {
		return thirty;
	}
	public void setThirty(Date thirty) {
		this.thirtyStr=CommonUtils.date2string(thirty, FinalConfigParam.DATE_FORMAT_STYLE);
		this.thirty = thirty;
	}
	
	
	
	
	
	public String getEightStr() {
		return eightStr;
	}
	public void setEightStr(String eightStr) {
		this.eight=CommonUtils.getDateFromString(eightStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.eightStr = eightStr;
	}
	public String getNineStr() {
		return nineStr;
	}
	public void setNineStr(String nineStr) {
		this.nine=CommonUtils.getDateFromString(nineStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.nineStr = nineStr;
	}
	public String getTenStr() {
		return tenStr;
	}
	public void setTenStr(String tenStr) {
		this.ten=CommonUtils.getDateFromString(tenStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.tenStr = tenStr;
	}
	public String getElevenStr() {
		return elevenStr;
	}
	public void setElevenStr(String elevenStr) {
		this.eleven=CommonUtils.getDateFromString(elevenStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.elevenStr = elevenStr;
	}
	public String getTwelveStr() {
		return twelveStr;
	}
	public void setTwelveStr(String twelveStr) {
		this.twelve=CommonUtils.getDateFromString(twelveStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twelveStr = twelveStr;
	}
	public String getThirteenStr() {
		return thirteenStr;
	}
	public void setThirteenStr(String thirteenStr) {
		this.thirteen=CommonUtils.getDateFromString(thirteenStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.thirteenStr = thirteenStr;
	}
	public String getForteenStr() {
		return forteenStr;
	}
	public void setForteenStr(String forteenStr) {
		this.forteen=CommonUtils.getDateFromString(forteenStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.forteenStr = forteenStr;
	}
	public String getFifteenStr() {
		return fifteenStr;
	}
	public void setFifteenStr(String fifteenStr) {
		this.fifteen=CommonUtils.getDateFromString(fifteenStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.fifteenStr = fifteenStr;
	}
	public String getSixteenStr() {
		return sixteenStr;
	}
	public void setSixteenStr(String sixteenStr) {
		this.sixteen=CommonUtils.getDateFromString(sixteenStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.sixteenStr = sixteenStr;
	}
	public String getSeventeenStr() {
		return seventeenStr;
	}
	public void setSeventeenStr(String seventeenStr) {
		this.seventeen=CommonUtils.getDateFromString(seventeenStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.seventeenStr = seventeenStr;
	}
	public String getEighteenStr() {
		return eighteenStr;
	}
	public void setEighteenStr(String eighteenStr) {
		this.eighteen=CommonUtils.getDateFromString(eighteenStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.eighteenStr = eighteenStr;
	}
	public String getNineteenStr() {
		return nineteenStr;
	}
	public void setNineteenStr(String nineteenStr) {
		this.nineteen=CommonUtils.getDateFromString(nineteenStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.nineteenStr = nineteenStr;
	}
	public String getTwentyStr() {
		return twentyStr;
	}
	public void setTwentyStr(String twentyStr) {
		this.twenty=CommonUtils.getDateFromString(twentyStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twentyStr = twentyStr;
	}
	public String getTwentyOneStr() {
		return twentyOneStr;
	}
	public void setTwentyOneStr(String twentyOneStr) {
		this.twentyOne=CommonUtils.getDateFromString(twentyOneStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twentyOneStr = twentyOneStr;
	}
	public String getTwentyTwoStr() {
		return twentyTwoStr;
	}
	public void setTwentyTwoStr(String twentyTwoStr) {
		this.twentyTwo=CommonUtils.getDateFromString(twentyTwoStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twentyTwoStr = twentyTwoStr;
	}
	public String getTwentyThreeStr() {
		return twentyThreeStr;
	}
	public void setTwentyThreeStr(String twentyThreeStr) {
		this.twentyThree=CommonUtils.getDateFromString(twentyThreeStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twentyThreeStr = twentyThreeStr;
	}
	public String getTwentyFourStr() {
		return twentyFourStr;
	}
	public void setTwentyFourStr(String twentyFourStr) {
		this.twentyFour=CommonUtils.getDateFromString(twentyFourStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twentyFourStr = twentyFourStr;
	}
	public String getTwentyFiveStr() {
		return twentyFiveStr;
	}
	public void setTwentyFiveStr(String twentyFiveStr) {
		this.twentyFive=CommonUtils.getDateFromString(twentyFiveStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twentyFiveStr = twentyFiveStr;
	}
	public String getTwentySixStr() {
		return twentySixStr;
	}
	public void setTwentySixStr(String twentySixStr) {
		this.twentySix=CommonUtils.getDateFromString(twentySixStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twentySixStr = twentySixStr;
	}
	public String getTwentySevenStr() {
		return twentySevenStr;
	}
	public void setTwentySevenStr(String twentySevenStr) {
		this.twentySeven=CommonUtils.getDateFromString(twentySevenStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twentySevenStr = twentySevenStr;
	}
	public String getTwentyEightStr() {
		return twentyEightStr;
	}
	public void setTwentyEightStr(String twentyEightStr) {
		this.twentyEight=CommonUtils.getDateFromString(twentyEightStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twentyEightStr = twentyEightStr;
	}
	public String getTwentyNineStr() {
		return twentyNineStr;
	}
	public void setTwentyNineStr(String twentyNineStr) {
		this.twentyNine=CommonUtils.getDateFromString(twentyNineStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.twentyNineStr = twentyNineStr;
	}
	public String getThirtyStr() {
		return thirtyStr;
	}
	public void setThirtyStr(String thirtyStr) {
		this.thirty=CommonUtils.getDateFromString(thirtyStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.thirtyStr = thirtyStr;
	}
	
	
	
	public Date getYi() {
		return yi;
	}
	public void setYi(Date yi) {
		this.yiStr=CommonUtils.date2string(yi, FinalConfigParam.DATE_FORMAT_STYLE);
		this.yi = yi;
	}
	public String getYiStr() {
		return yiStr;
	}
	public void setYiStr(String yiStr) {
		this.yi=CommonUtils.getDateFromString(yiStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.yiStr = yiStr;
	}
	public Date getEr() {
		return er;
	}
	
	
	public void setEr(Date er) {
		this.erStr=CommonUtils.date2string(er, FinalConfigParam.DATE_FORMAT_STYLE);
		this.er = er;
	}
	public String getErStr() {
		return erStr;
	}
	public void setErStr(String erStr) {
		this.er=CommonUtils.getDateFromString(erStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.erStr = erStr;
	}
	public Date getSa() {
		return sa;
	}
	public void setSa(Date sa) {
		this.saStr=CommonUtils.date2string(sa, FinalConfigParam.DATE_FORMAT_STYLE);
		this.sa = sa;
	}
	public String getSaStr() {
		return saStr;
	}
	public void setSaStr(String saStr) {
		this.sa=CommonUtils.getDateFromString(saStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.saStr = saStr;
	}
	public Date getSi() {
		return si;
	}
	public void setSi(Date si) {
		this.siStr=CommonUtils.date2string(si, FinalConfigParam.DATE_FORMAT_STYLE);
		this.si = si;
	}
	public String getSiStr() {
		return siStr;
	}
	public void setSiStr(String siStr) {
		this.si=CommonUtils.getDateFromString(siStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.siStr = siStr;
	}
	public Date getWu() {
		return wu;
	}
	public void setWu(Date wu) {
		this.wuStr=CommonUtils.date2string(wu, FinalConfigParam.DATE_FORMAT_STYLE);
		this.wu = wu;
	}
	public String getWuStr() {
		return wuStr;
	}
	public void setWuStr(String wuStr) {
		this.wu=CommonUtils.getDateFromString(wuStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.wuStr = wuStr;
	}
	public Date getLiu() {
		return liu;
	}
	public void setLiu(Date liu) {
		this.liuStr=CommonUtils.date2string(liu, FinalConfigParam.DATE_FORMAT_STYLE);
		this.liu = liu;
	}
	public String getLiuStr() {
		return liuStr;
	}
	public void setLiuStr(String liuStr) {
		this.liu=CommonUtils.getDateFromString(liuStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.liuStr = liuStr;
	}
	public Date getQi() {
		return qi;
	}
	public void setQi(Date qi) {
		this.qiStr=CommonUtils.date2string(qi, FinalConfigParam.DATE_FORMAT_STYLE);
		this.qi = qi;
	}
	public String getQiStr() {
		return qiStr;
	}
	public void setQiStr(String qiStr) {
		this.qi=CommonUtils.getDateFromString(qiStr, FinalConfigParam.DATE_FORMAT_STYLE);
		this.qiStr = qiStr;
	}
	public int getYiwNum() {
		return yiwNum;
	}
	public void setYiwNum(int yiwNum) {
		this.yiwNum = yiwNum;
	}
	public int getErNum() {
		return erNum;
	}
	public void setErNum(int erNum) {
		this.erNum = erNum;
	}
	public int getSaNum() {
		return saNum;
	}
	public void setSaNum(int saNum) {
		this.saNum = saNum;
	}
	public int getSiNum() {
		return siNum;
	}
	public void setSiNum(int siNum) {
		this.siNum = siNum;
	}
	public int getWuNum() {
		return wuNum;
	}
	public void setWuNum(int wuNum) {
		this.wuNum = wuNum;
	}
	public int getLiuNum() {
		return liuNum;
	}
	public void setLiuNum(int liuNum) {
		this.liuNum = liuNum;
	}
	public int getQiNum() {
		return qiNum;
	}
	public void setQiNum(int qiNum) {
		this.qiNum = qiNum;
	}
	public int getJanuary() {
		return january;
	}
	public void setJanuary(int january) {
		this.january = january;
	}
	public int getFebruary() {
		return february;
	}
	public void setFebruary(int february) {
		this.february = february;
	}
	public int getMarch() {
		return march;
	}
	public void setMarch(int march) {
		this.march = march;
	}
	public int getApril() {
		return april;
	}
	public void setApril(int april) {
		this.april = april;
	}
	public int getMay() {
		return may;
	}
	public void setMay(int may) {
		this.may = may;
	}
	public int getJune() {
		return june;
	}
	public void setJune(int june) {
		this.june = june;
	}
	public int getJuly() {
		return july;
	}
	public void setJuly(int july) {
		this.july = july;
	}
	public int getAugust() {
		return august;
	}
	public void setAugust(int august) {
		this.august = august;
	}
	public int getSeptember() {
		return september;
	}
	public void setSeptember(int september) {
		this.september = september;
	}
	public int getOctober() {
		return october;
	}
	public void setOctober(int october) {
		this.october = october;
	}
	public int getNovember() {
		return november;
	}
	public void setNovember(int november) {
		this.november = november;
	}
	public int getDecember() {
		return december;
	}
	public void setDecember(int december) {
		this.december = december;
	}
	
	
}
