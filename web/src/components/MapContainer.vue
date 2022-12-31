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
        var colors = {};
        var GDPSpeed = {
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
              if(GDPSpeed[t2] < city.grade)
              {
                GDPSpeed[t2] = city.grade
              }
            }
          })
        }
        freshSelect();

        var getColorByDGP = function (adcode) {
          if (!colors[adcode]) {
            var risk = GDPSpeed[adcode];
            if (risk == 0) {
              colors[adcode] = 'rgb(227,227,227)'
            } else if (risk == 1) {
              colors[adcode] = 'rgb(245,156,179)'
            } else if (risk == 2) {
              colors[adcode] = 'rgb(238,97,134)'
            } else if (risk == 3) {
              colors[adcode] = 'rgb(234,57,104)'
            } else if (risk == 4) {
              colors[adcode] = 'rgb(117,29,52)'
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

        /*new AMapUI.SimpleMarker({
          //前景文字
          iconLabel: 'A',
          //图标主题
          iconTheme: 'default',
          //背景图标样式
          iconStyle: 'red',
          map: this.map,
          position: this.map.getCenter()
        });*/

        /*var pathSimplifierIns = new AMapUI.PathSimplifier({
          zIndex: 100,
          map: this.map, //所属的地图实例
          getPath: function (pathData, pathIndex) {
            //返回轨迹数据中的节点坐标信息，[AMap.LngLat, AMap.LngLat...] 或者 [[lng|number,lat|number],...]
            return pathData.path;
          },
          getHoverTitle: function (pathData, pathIndex, pointIndex) {
            //返回鼠标悬停时显示的信息
            if (pointIndex >= 0) {
              //鼠标悬停在某个轨迹节点上
              return pathData.name + '，点:' + pointIndex + '/' + pathData.path.length;
            }
            //鼠标悬停在节点之间的连线上
            return pathData.name + '，点数量' + pathData.path.length;
          },
          renderOptions: {
            //轨迹线的样式
            pathLineStyle: {
              strokeStyle: 'red',
              lineWidth: 6,
              dirArrowStyle: true
            }
          }
        });

        //这里构建两条简单的轨迹，仅作示例
        pathSimplifierIns.setData([{
          name: '轨迹0',
          path: [
            [100.340417, 27.376994],
            [108.426354, 37.827452],
            [113.392174, 31.208439],
            [124.905846, 42.232876]
          ]
        }, {
          name: '大地线',
          //创建一条包括500个插值点的大地线
          path: AMapUI.PathSimplifier.getGeodesicPath([116.405289, 39.904987], [87.61792, 43.793308], 500)
        }]);
        var navg0 = pathSimplifierIns.createPathNavigator(0, //关联第1条轨迹
          {
            loop: true, //循环播放
            speed: 1000000
          });

        navg0.start();*/

      }).catch(e => {
        console.log(e);
      })
    },
    drawpath() {
      //eslint-disable-next-line no-undef
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
          // eslint-disable-next-line no-unused-vars
          getPath: function (pathData, pathIndex) {
            //返回轨迹数据中的节点坐标信息，[AMap.LngLat, AMap.LngLat...] 或者 [[lng|number,lat|number],...]
            return pathData.path;
          },
          getHoverTitle: function (pathData, pathIndex, pointIndex) {
            //返回鼠标悬停时显示的信息
            if (pointIndex >= 0) {
              //鼠标悬停在某个轨迹节点上
              return pathData.name + '，点:' + pointIndex + '/' + pathData.path.length;
            }
            //鼠标悬停在节点之间的连线上
            return pathData.name + '，点数量' + pathData.path.length;
          },
          renderOptions: {
            //轨迹线的样式
            pathLineStyle: {
              strokeStyle: 'red',
              lineWidth: 6,
              dirArrowStyle: true
            }
          }
        });

        //这里构建两条简单的轨迹，仅作示例
        pathSimplifierIns.setData([{
          name: '轨迹0',
          path: [
            [100.340417, 27.376994],
            [108.426354, 37.827452],
            [113.392174, 31.208439],
            [124.905846, 42.232876]
          ]
        }, {
          name: '大地线',
          //创建一条包括500个插值点的大地线
          path: PathSimplifier.getGeodesicPath([116.405289, 39.904987], [87.61792, 43.793308], 500)
        }]);

        let navg0 = pathSimplifierIns.createPathNavigator(0, //关联第1条轨迹
            {
              speed: 1000000,
              pathNavigatorStyle:{
                content: PathSimplifier.Render.Canvas.getImageContent(
                  "https://s2.loli.net/2022/11/19/3fL4dZzHC5Mosep.png",
                    function onload() {
                      pathSimplifierIns.renderLater();
                    },
                    function onerror() {
                      alert('图片加载失败！');
                    }),
              }
            });

        navg0.start();
      }
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