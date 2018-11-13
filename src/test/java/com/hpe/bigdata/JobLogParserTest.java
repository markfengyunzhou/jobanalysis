package com.hpe.bigdata;

import org.junit.Test;

import static org.junit.Assert.*;

public class JobLogParserTest {

    @Test
    public void main() {

        String logcontext = "{'number': 'CC000022144J00097868011', 'jobType': {'items': [{'code': '160000', 'name': '软件/互>联网开发/系统集成'}, {'code': '2034', 'name': '算法工程师'}], 'display': '软件/互联网开发/系统集\n" +
                "成,算法工程师'}, 'company': {'number': 'CZ000022140', 'url': 'https://company.zhaopin.com/CZ000022140.htm', 'name': '新浪网', 'size': {'code': '5', 'name': '1000-9999人'}, 'type': {'code': '2', 'name': '外商独资'}}, 'positionURL': 'https://jobs.zhaopin.com/CC000022144J00097868011.htm', 'workingExp': {'code': '103', 'name': '1-3年'}, 'eduLevel': {'code': '4', 'name': '本科'}, 'salary': '20K-40K', 'emplType': '全职', 'jobName': '语音识别中高级算法工程师', 'industry': '160400,210500', 'recruitCount': 0, 'geo': {'lat': '30.335525', 'lon': '120.119823'}, 'city': {'items': [{'code': '653', 'name': '杭州'}, {'code': '2236', 'name': '拱墅区'}], 'display': '杭州-拱墅区'}, 'applyType': '1', 'updateDate': '2018-09-25 17:14:23', 'createDate': '2018-09-25 17:14:22', 'endDate': '2018-10-25 17:14:22', 'welfare': ['健身俱乐部', '定期体检', '弹性工作'], 'saleType': 0, 'feedbackRation': 0, 'score': 142, 'resumeCount': 8, 'showLicence': 0, 'interview': 0, 'companyLogo': 'http://company.zhaopin.com/CompanyLogo/20140416/41630E4D4AC743178BB16359FF4F9DFE.JPG', 'tags': [], 'vipLevel': 1003, 'expandCount': 0, 'positionLabel': '{\"qualifications\":null,\"role\":null,\"level\":null,\"jobLight\":[\"健身俱乐部\",\"弹性工作\",\"定期体检\"],\"companyTag\":[\"健身俱乐部\",\"定期\n" +
                "体检\"],\"skill\":null}', 'duplicated': False, 'futureJob': False, 'selected': False, 'applied': False, 'collected': False, 'isShow': False, 'timeState': '招聘中', 'rate': ''}";

        JobLogParser dp = new JobLogParser();
        System.out.println(dp.parse(logcontext)[0]);
        System.out.println(dp.parse(logcontext)[1]);

        String log = "https://jobs.zhaopin.com/CC000022144J00097868011.htm:['\\n', '\\n职位描述\\n', '\\n', '\\n', '岗位职责：', ' ', '1. 语音识别技术及相关算法的研究与开发；', ' 2. 语音识别系统的数据处理、模型训练、结果分析、>实验验证；', ' 3. 基于海量数据的模型训练、词汇量连续语音识别系统的开发;', ' 4. 跟踪国内外最新的语音>识别相关先进技术。', '岗位要求：', ' ', '1、硕士及以上学历，信号处理、计算机、电子信息等相关专业；', ' 2、必须有Linux操作系统下C/C++/Java 及 Python/Perl 编程的扎实功力及经验；', ' 3、必须有Kaldi，CMU Sphinx 等开源项目经验；', ' 4、精通语音识别/模式识别/神经网络/信号处理、机器学习等相关算法;', ' 5、>熟悉科大讯飞语音平台者优先；', ' 6、有大型公司语音开发经验者优先 (科大讯飞、云知声、盛大、捷通华声、\n" +
                "搜狗语音助手、百度语音，微软小娜 Cortana)；', ' 7、较强的沟通和表达能力，良好的抗压能力，有较强的自>学习能力和团队协作精神；英文良好。', '\\n', '\\n', '\\n', '展开', '\\n', '\\n', '\\n']";


        log = log.replaceAll("\n", "").
                replaceAll("\t", "").
                replaceAll("\r", "").replaceAll("\\n", "")
                .replaceAll("\\\n", "").replaceAll("\\\\n", "")
                .replaceAll(" ", "").replaceAll("'", "");

        System.out.println(dp.parse(log)[0]);
        System.out.println(dp.parse(log)[1]);
    }
}