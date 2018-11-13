package com.hpe.bigdata;

import net.sf.json.JSONObject;

import net.sf.ezmorph.Morpher;

public class JobLogParser {

    final String jobInfoFields = "number,jobType,company,positionURL,workingExp,eduLevel,salary,emplType,jobName,industry,recruitCount,geo,city,applyType,updateDate,createDate,endDate,welfare,saleType,feedbackRation,score,resumeCount,showLicence,interview,companyLogo,tags,vipLevel,expandCount,positionLabel,duplicated,futureJob,selected,applied,collected,isShow,timeState,rate";

    public enum logTypeEnum {
        jobInfo, jobDetail
    }

    public JobLogParser() {

    }

    /**
     * 日志类型分类器
     *
     * @return
     */
    private logTypeEnum typeClassfier(String logContent) {
        if (logContent.startsWith("http"))
            return logTypeEnum.jobDetail;
        else
            return logTypeEnum.jobInfo;
    }

    /**
     * 解析日志
     *
     * @param logContent
     * @return
     */
    public String[] parse(String logContent) {

        logTypeEnum logType = typeClassfier(logContent);

        if (logType.equals(logTypeEnum.jobInfo)) {

            return new String[]{logType.toString(), jobInfoParse(logContent)};

        } else {
            return new String[]{logType.toString(), jobDetailParse(logContent)};
        }
    }

    /**
     * 解析jobInfo
     *
     * @param logContent
     * @return
     */
    private String jobInfoParse(String logContent) {
        StringBuilder sb = new StringBuilder();
        logContent = logContent.replaceAll("\n", "").
                replaceAll("\t", "").
                replaceAll("\r", "");

        JSONObject jsonObj = null;

        try {
            jsonObj = JSONObject.fromObject(logContent);
        } catch (Exception e) {
            System.out.println(e.toString() + logContent);
            return sb.toString();
        }
        for (String field : jobInfoFields.split(",")) {
            sb.append(jsonObj.get(field));
            sb.append("\t");
        }

        return sb.toString();

    }

    /**
     * 解析jobDetail
     *
     * @param logContent
     * @return
     */
    private String jobDetailParse(String logContent) {
        StringBuilder sb = new StringBuilder();
        logContent = logContent.replaceAll("\n", "")
                .replaceAll("\t", "")
                .replaceAll("\r", "").replaceAll("\\n", "")
                .replaceAll("\\\n", "").replaceAll("\\\\n", "")
                .replaceAll(" ", "").replaceAll("'", "");


        if (logContent.indexOf("[") != -1) {
            sb.append(logContent.substring(0, logContent.indexOf(":[")));
            sb.append("\t");
            sb.append(logContent.substring(logContent.indexOf("[")));
        }

        return sb.toString();


    }


    public static void main(String[] args) {


    }
}
