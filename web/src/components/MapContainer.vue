<template>
  <el-card class="box-card">
    <div id="container">
    </div>
  </el-card>

</template>

<script>
import AMapLoader from '@amap/amap-jsapi-loader';
import { shallowRef } from '@vue/reactivity';
import axios from "axios"
import AdcodeToLng from "@/utils/queryLng"

export default {
  setup() {
    const map = shallowRef(null);
    return {
      map,
    }
  },
  create() {

  },
  methods: {
    ininMap() {
      window._AMapSecurityConfig = { securityJsCode: '374ed8dd1dc99358268d7529ca09928c' };

      AMapLoader.load({
        key: "ee9e3b30465a61728e51d506defb1b2a",
        version: "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        AMapUI: {
          // 是否加载 AMapUI，缺省不加载
          version: "1.1", // AMapUI 缺省 1.1
          plugins: ['misc/PathSimplifier', 'control/BasicControl', 'overlay/SimpleMarker'], // 需要加载的 AMapUI ui插件
        },
        plugins: ["AMap.DistrictLayer", "AMap.Scale", "AMap.ToolBar"],
        Loca: {
          // 是否加载 Loca， 缺省不加载
          version: "2.0.0", // Loca 版本，缺省 2.0.0
        },
      }).then((AMap) => {
        const colors = {};
        const GDPSpeed = {
          '520000': 0,//贵州
          '540000': 0,//西藏
          '530000': 0,//云南 
          '500000': 0,//重庆
          '360000': 0,//江西
          '340000': 0,//安徽
          '510000': 0,//四川
          '350000': 0,//福建
          '430000': 0,//湖南
          '420000': 0, //湖北
          '410000': 0,//河南
          '330000': 0,//浙江
          '640000': 0,//宁夏
          '650000': 0,//新疆
          '440000': 0,//广东
          '370000': 0,//山东
          '450000': 0,//广西
          '630000': 0,//青海
          '320000': 0,//江苏
          '140000': 0,//山西
          '460000': 0,// 海南
          '310000': 0,//上海
          '110000': 0, // 北京
          '130000': 0, // 河北
          '230000': 0, // 黑龙江
          '220000': 0,// 吉林
          '210000': 0, //辽宁
          '150000': 0,//内蒙古
          '120000': 0,// 天津
          '620000': 0,// 甘肃
          '610000': 0,// 陕西
          '710000': 0, //台湾
          '810000': 0, //香港
          '820000': 0 //澳门
        }

        const freshSelect = () => {
          axios({
            url: "http://localhost:3000/api/back/getcities/",
            method: "GET",
            headers: {
              Authorization: "Bearer " + localStorage.getItem("jwt_token"),
            },
          }).then((resp) => {
            let cities = resp.data;
            for (let city of cities) {
              var t = city.cityAdcode.toString()
              var t1 = t.substring(0, 2)
              var t2 = t1.concat("0000")
              if (GDPSpeed[t2] < city.grade) {
                GDPSpeed[t2] = city.grade
              }
            }
          })
        }
        freshSelect();

        const getColorByDGP = function (adcode) {
          if (!colors[adcode]) {
            const risk = GDPSpeed[adcode];
            if (risk == 0) {
              colors[adcode] = 'rgb(227,227,227)'
            } else if (risk == 1) {
              colors[adcode] = 'rgb(245,156,179)'
            } else if (risk == 2) {
              colors[adcode] = 'rgb(234,57,104)'
            }
          }
          return colors[adcode]
        }

        var defaultLayer = new AMap.createDefaultLayer();

        var disCountry = new AMap.DistrictLayer.Country({
          zIndex: 10,
          SOC: 'CHN',
          depth: 1,
          styles: {
            'nation-stroke': '#000000',
            'coastline-stroke': [0.85, 0.63, 0.94, 1],
            'province-stroke': '#000000',
            'fill': function (props) {//中国特有字段
              return getColorByDGP(props.adcode_pro)
            }
          }
        })

        this.map = new AMap.Map("container", {
          //设置地图容器id
          zoom: 4.4, //初始化地图级别
          mapStyle:
            "amap://styles/8389cc93e89d4cf61b6873b24befc228", //设置地图的显示样式
          center: [106.542725, 36.583360], //初始化地图中心点位置//
          layers: [
            defaultLayer,
            disCountry
          ],
          features: ['bg', 'building', 'point'],
        });

        window.map = this.map;

      }).catch(e => {
        console.log(e);
      })
    },
    drawpath(paths) {
      if (window.pathSimplifierIns) {
        //通过该方法清空上次传入的轨迹
        pathSimplifierIns.setData([]);
      }

      //经纬度数据处理
      let temp = [];
      for (let i = 0; i < paths.length; i++) {
        if (i != paths.length - 1) {
          let t = this.cityLng(paths[i].startCity.toString());
          let t1 = t.split(',')
          for (let j = 0; j < t1.length; j++) {
            t1[j] = parseFloat(t1[j])
          }
          t1.push(paths[i].type)
          temp.push(t1)
        }
        else {
          let t = this.cityLng(paths[i].startCity.toString());
          let t1 = t.split(',')
          for (let j = 0; j < t1.length; j++) {
            t1[j] = parseFloat(t1[j])
          }
          t1.push(paths[i].type)
          temp.push(t1)

          t = this.cityLng(paths[i].endCity.toString());
          t1 = t.split(',')
          for (let j = 0; j < t1.length; j++) {
            t1[j] = parseFloat(t1[j])
          }
          t1.push(paths[i].type)
          temp.push(t1)
        }
      }

      //创建轨迹组件
      AMapUI.load(['ui/misc/PathSimplifier'], (PathSimplifier) => {

        if (!PathSimplifier.supportCanvas) {
          alert('当前浏览器不支持轨迹动画！');
          return;
        }

        //启动页面
        initPage(PathSimplifier);

      });

      function initPage(PathSimplifier) {

        //创建组件实例
        let pathSimplifierIns = new PathSimplifier({
          zIndex: 100,
          map: window.map, //所属的地图实例
          getPath: function (pathData, pathIndex) {
            return pathData.path;
          },
          getHoverTitle: function (pathData, pathIndex, pointIndex) {
            return null;
          },
          autoSetFitView: false,
          renderOptions: {
            //轨迹线的样式
            pathLineStyle: {
              strokeStyle: 'rgb(160,219,142)',
              lineWidth: 8,
              dirArrowStyle: true
            }
          }
        });

        window.pathSimplifierIns = pathSimplifierIns

        pathSimplifierIns.setData([{
          name: '规划轨迹',
          path: temp,
        },]);

        //
        function onload() {
          pathSimplifierIns.renderLater();
        }

        function onerror(e) {
          alert('图片加载失败！');
        }

        var pathNavigatorStyles = [{
          //飞机
          width: 30,
          height: 30,
          pathLinePassedStyle: {
            lineWidth: 8,
            strokeStyle: 'rgb(51,153,255)',
          },
          content: PathSimplifier.Render.Canvas.getImageContent("https://i.328888.xyz/2023/01/05/WNR3X.png", onload, onerror),
        },
        {
          //汽车
          width: 30,
          height: 30,
          pathLinePassedStyle: {
            lineWidth: 8,
            strokeStyle: 'rgb(51,153,255)',
          },
          content: PathSimplifier.Render.Canvas.getImageContent("https://i.328888.xyz/2023/01/05/WNcSt.png", onload, onerror),
        },
        {
          //火车
          width: 30,
          height: 30,
          pathLinePassedStyle: {
            lineWidth: 8,
            strokeStyle: 'rgb(51,153,255)',
          },
          content: PathSimplifier.Render.Canvas.getImageContent('https://i.328888.xyz/2023/01/05/WN3DP.png', onload, onerror),
        }
        ]

        //覆盖方法
        function extend(dst) {
          if (!dst) {
            dst = {};
          }
          var slist = Array.prototype.slice.call(arguments, 1);
          for (var i = 0, len = slist.length; i < len; i++) {
            var source = slist[i];
            if (!source) {
              continue;
            }
            for (var prop in source) {
              if (source.hasOwnProperty(prop)) {
                dst[prop] = source[prop];
              }
            }
          }
          return dst;
        }

        //设置巡航器
        let navg0 = pathSimplifierIns.createPathNavigator(0, //关联第1条轨迹
          {
            speed: 500000,
            pathNavigatorStyle: extend({}, pathNavigatorStyles[pathSimplifierIns._data.source[0].path[0][2]])
          });
        navg0.start();

        //改变巡航器样式
        function changeNavgContent() {

          //获取到pathNavigatorStyle的引用
          var pathNavigatorStyle = navg0.getStyleOptions();

          //覆盖修改
          let now = navg0.getCursor().idx;
          let typetemp = pathSimplifierIns._data.source[0].path[now];
          let type = typetemp[2];
          extend(pathNavigatorStyle, pathNavigatorStyles[type % 3]);

          //重新绘制
          pathSimplifierIns.renderLater();
        }
        setInterval(changeNavgContent, 50);
      }
    },
    cityLng: function (adcode) {
      return AdcodeToLng.get(adcode.toString());
    },

  },
  mounted() {
    //DOM初始化完成进行地图初始化
    this.ininMap();
  }
}
</script>



<style scoped>
#container {
  width: 100%;
  height: 700px;
}
</style>



<style>
/* 隐藏高德logo  */
.amap-logo {
  display: none !important;
}

/* 隐藏高德版权  */
.amap-copyright {
  display: none !important;
}
</style>