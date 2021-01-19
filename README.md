# SitNews 上应新闻搜索 API

## 概要

本项目为 2020-2021 年度第一学期 "Java企业级项目实训（B7042653）" 的课程设计，这个仓库只包含 API 部分。

项目的 API 部分使用 Java 语言开发，平台为：

- Windows 10 x86_64
- JDK 15
- Java bytecode 1.8

主要依赖库的参数如下：

- Springboot 2.4.1
- ElasticSearch 7.10.1
- Gson 2.8.6
- Jsoup 1.11.3
- Okhttp3 4.9.0

## API 接口

项目接口主要有：

- GET /news/{docId} 查看网页缓存
- GET /news/ 新闻搜索
- GET /news/suggestion/ （未完全实现）

### GET /news/{**docId**}

查看特定新闻的网页缓存。

#### 参数

- `docId`  文档 ID。在返回搜索结果时会提供。

#### 示例

请求地址

```http://localhost:8080/news/99462643-1ba3-4cea-bb1d-2024fe211318```

响应

```json
{
  "docId": "99462643-1ba3-4cea-bb1d-2024fe211318",
  "title": "【媒体关注】科学系统推进劳动教育",
  "url": "https://www.sit.edu.cn/_t470/2020/0514/c12988a185089/page.htm",
  "author": "郭东波",
  "date": "2020-05-14",
  "description": "　　2020年05月13日 08:15<br>　　来源：中国社会科学网-中国社会科学报　作者：查建国 陈炼<br>　　链接：http://www.cssn.cn/zx/bwyc/202005/t20200513_5127793.shtml?COLLCC=3281719987<br>　　中国社会科学报讯 （记者查建国 陈炼）近日，由上海应用技术大学主办的新时代劳动教育创新论坛暨德育研究中心揭牌仪式在沪举行。来自多所高校和机构的专家学者、劳动模范以及师生代表参加了仪式。<br>　　上海应用技术大学党委书记郭庆松表示，一所特色鲜明的应用创新型大学，应立足于应用技术型人才培养的特点，科学系统地推进劳动教育，探索构建应用型高校劳动教育体系，培育学生正确的劳动价值观、建设劳动教育课程群、开展劳动教育实践系、打造劳动教育共同体，使劳动教育成为新时代学校砥砺前行、创造美好生活最有力的实践。<br>　　上海市教委德育处副处长杨长亮提出，要高度重视劳动教育，始终坚持我国社会主义办学方向。应围绕立德树人根本任务，把劳动教育作为促进学生全面发展的重要环节，以日常生活、生产实践和社会服务为主要内容，持续推动劳动教育工作。发挥劳动教育的树德、增智、强体、育美作用，切实提高劳动教育成效，构建德智体美劳有机融合发展的学生综合素养培育系统，把劳动教育作为开门办教育的“试验田”。<br>　　国防大学政治学院教授孙力认为，新时代的劳动蕴含终身学习的理念，因此无论是简单劳动还是复杂劳动，都应树立先培训后上岗和终身学习的观念。此外，创新性劳动是劳动演进的必然结果，也印证了人民群众才是推动社会发展的力量源泉。<br>　　上海师范大学教授何云峰表示，劳动精神要通过具体的劳动者去弘扬。社会主义劳动教育既要注重展示劳动成效，还要大力弘扬奉献精神。<br>　　复旦大学教授陈学明提出，劳动是人的自由全面发展的前提条件，也是人的内在潜能得以外在化的体现，是实现自我和自我实现的重要途径。高校应根据自身办学特色将劳动教育作为德育重点，聚焦马克思主义劳动理论和实践教育，开展特色化劳动教育模式。<br>"
}
```

### GET /news/

搜索新闻

#### 参数

- `q`  要查询的字符串
- `sort`  查询类别。其可选值为 `title` 或 `author`，表示在特定字段中查找。若不填或错填，则表示全文检索。
- `page`  分页查询时的页码，默认为 `0`
- `count`  每一页查询的数目，默认为 `10`

#### 示例

请求地址

`http://localhost:8080/news/?q=%E5%AA%92%E4%BD%93%E5%85%B3%E6%B3%A8&page=0&count=3&sort=title`

响应

```json
{
	"costTime": 0.005,
  "hits": 3,
  "total": 242,
  "results": [
    {
		"docId": "99462643-1ba3-4cea-bb1d-2024fe211318",
		"title": "【媒体关注】科学系统推进劳动教育",
		"url": "https://www.sit.edu.cn/_t470/2020/0514/c12988a185089/page.htm",
		"author": "郭东波",
		"date": "2020-05-14",
		"description": "　　2020年05月13日 08:15\n　　来源：中国社会科学网-中国社会科学报　作者：查建国 陈炼\n　　链接：http://www.cssn.cn/zx/bwyc/202005/t20200513_..."
	}, {
		"docId": "9c4aa7cc-7dd4-49d5-83c3-1affd3280523",
		"title": "【媒体关注】新方向 新标准 新作为",
		"url": "https://www.sit.edu.cn/_t470/2017/1205/c12988a152634/page.htm",
		"author": "董国文",
		"date": "2017-12-05",
		"description": "　　来源:《中国花卉报》，2017年11月30日，02版\n　　本报记者 薛光卿 　　上海应用技术大学是生态技术与工程学院党总支书记曹扬曾荣获上海市教卫党委系统优秀党务工作者、上海市首届“教学新星”提名..."
	}, {
      "docId": "e468b5f8-f3cc-4be5-ac2a-3646dd692a7a",
      "title": "【媒体关注】上海应用技术大学绿色环保项目引人关注",
      "url": "https://www.sit.edu.cn/_t470/2019/0920/c12988a178740/page.htm",
      "author": "董国文",
      "date": "2019-09-20",
      "description": "　　来 源：《上海科技报》，2019年09月19日，B1版\n　　在第21届中国国际工业博览会上，聚焦绿色、环保、芳香等紧扣学校专业特色的主题，上海应用技术大学共有21个项目亮相高校展区。\n　　新科技让..."
    }
  ]
}
```

## GET /news/recent

获取最近十条新闻

### 参数

无

### 示例

请求地址

`http://localhost:8080/news/recent`

响应

```json
{
  "costTime": 0.116,
  "count": 10,
  "recentItems": [
    {
      "docId": "b08c96a1-38b3-4457-8220-74c660d72a48",
      "title": "校党委召开二级党组织书记抓基层党建工作述职评议会",
      "url": "https://www.sit.edu.cn/_t470/2021/0112/c12988a192557/page.htm"
    },
    {
      "docId": "0232dd2d-81fc-46cc-b1f3-565643086106",
      "title": "校党委书记郭庆松出席城建学院2020年度工作总结暨迎新茶话会活动",
      "url": "https://www.sit.edu.cn/_t470/2021/0112/c12988a192556/page.htm"
    },
    {
      "docId": "f73af068-5773-4b5f-8f2a-90bfa6459094",
      "title": "校领导带队深入各学院调研人才队伍建设",
      "url": "https://www.sit.edu.cn/_t470/2021/0112/c12988a192555/page.htm"
    },
    {
      "docId": "e654d490-2e39-4021-8def-c491bb67d1da",
      "title": "学校召开2020年度本科教育教学会议",
      "url": "https://www.sit.edu.cn/_t470/2021/0109/c12988a192517/page.htm"
    },
    {
      "docId": "8b1ac928-3fa0-428b-8649-0ad104dafc84",
      "title": "校领导带队赴上海高等教育学会调研",
      "url": "https://www.sit.edu.cn/_t470/2021/0108/c12988a192514/page.htm"
    },
    {
      "docId": "e8a06562-95e6-41b6-a1c8-90e1f11e2119",
      "title": "学校召开2021年度国家基金申报工作推进会",
      "url": "https://www.sit.edu.cn/_t470/2021/0107/c12988a192494/page.htm"
    },
    {
      "docId": "f6e894c6-3c51-4504-9e10-28c59f2c937b",
      "title": "我校举行国家首批现代产业学院申报研讨会",
      "url": "https://www.sit.edu.cn/_t470/2021/0106/c12988a192472/page.htm"
    },
    {
      "docId": "202ba2c8-10bf-48e7-b32f-fb5366744508",
      "title": "上海应用技术大学2020年度十大新闻",
      "url": "https://www.sit.edu.cn/_t470/2021/0101/c12988a192326/page.htm"
    },
    {
      "docId": "e6904bd6-efaf-454b-8a10-36e9206fa716",
      "title": "2021年上海应用技术大学新年贺词",
      "url": "https://www.sit.edu.cn/_t470/2020/1231/c12988a192324/page.htm"
    },
    {
      "docId": "bd0e6f92-7adc-40f8-b245-fb489686513d",
      "title": "校党委书记郭庆松为机关第二党支部讲授专题党课",
      "url": "https://www.sit.edu.cn/_t470/2021/0103/c12988a192332/page.htm"
    }
  ]
}
```

## 开源协议

版权所有 (C) 2021 sunnysab

项目所提到的源代码和相关文件仅供学习和交流使用