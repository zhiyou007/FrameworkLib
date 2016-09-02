# FrameworkLib

有时候突然想写个APP，但要重新搞个开发框架很麻烦，所以就有了这个项目,等灵感来的时候就可以快速做成个APP，然后给投资人，然后顺利拿到投资，然后走上人生巅峰（上面都是我胡扯，忽略）<br><br><br><br>
一个APP开发必备的几个模块：<br>
1.首页（基本都是TAB+VIEWPAGER）<br>
2.网络（获取数据，更新UI,中间的专业术语很多啦，什么MVP模式,MVC模式(我觉得能抓老鼠都是好猫，所以这些随意了)...）<br>
3.图片（网络图片获取显示，图片处理等）<br>
4.列表（recycleview,listview... 还有他们必备的adapter）<br>
5.数据库（用户保存一些本地数据）<br>
6.其他一些可选的工具类库（比如缓冲层（可以用于减少服务器压力）...）<br><br><br><br>




此框架做了一下几个点<br>

1.baseactivity,basefragment(封装了一些基础操作，对外开放几个接口，减少重复劳动)<br>
2.通用adapter(适用listview,gridview,recycleview)<br>
3.网络库（OKHTTP）<br>
4.图片库（glide（主要因为它小，处理普通图片足够，但你如果有显示大图片的需求，比如几M的图片（包括GIF），那最好选fresco,正式项目最好选后者））<br>
  
