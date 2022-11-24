<template>
  <div id="container"></div>
</template>
<script>
import AMapLoader from '@amap/amap-jsapi-loader';
import { shallowRef } from '@vue/reactivity';

export default {
  setup(){
    const map = shallowRef(null);
    return{
      map,
    }
  },
  methods: {
    initMap() {
      AMapLoader.load({
        key: "13219835ccd60ca4ae01c2c13000e39d",// 申请好的Web端开发者Key，首次调用 load 时必填
        version: "2.0",      // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        plugins: [],
        AMapUI: {
          // 是否加载 AMapUI，缺省不加载
          version: "1.1", // AMapUI 缺省 1.1
          plugins: [], // 需要加载的 AMapUI ui插件
        },// 需要使用的的插件列表，如比例尺'AMap.Scale'等
      }).then((AMap) => {
        this.map = new AMap.Map("container", {  //设置地图容器id
          viewMode: "2D",    //是否为3D地图模式
          zoom: 5,           //初始化地图级别
          center: [106.542725, 31.583360], //初始化地图中心点位置//
        });
        window.map = this.map;
        this.drawProvince(window.map);
      }).catch(e => {
        console.log(e);
      })

    },

    drawProvince(map) {
      let gb = 200;
      let adcodes = [
        {adcode: "110000", color: "#ffffff"},
        {adcode: "120000", color: "#ffffff"},
        {adcode: "130000", color: "#ffffff"},
        {adcode: "140000", color: "#ea0606"},
        {adcode: "150000", color: "#ea0606"},

        {adcode: "210000", color: "#ea0606"},
        {adcode: "220000", color: "#ea0606"},
        {adcode: "230000", color: "#ea0606"},

        {adcode: "310000", color: "#f68e5c"},
        {adcode: "320000", color: "#f68e5c"},
        {adcode: "330000", color: "#f68e5c"},
        {adcode: "340000", color: 'rgb(' + gb + ',' + gb + ',255)'},
        {adcode: "350000", color: 'rgb(' + gb + ',' + gb + ',255)'},
        {adcode: "360000", color: 'rgb(' + gb + ',' + gb + ',255)'},
        {adcode: "370000", color: 'rgb(' + gb + ',' + gb + ',255)'},

        {adcode: "410000", color: 'rgb(' + gb + ',' + gb + ',255)'},
        {adcode: "420000", color: 'rgb(' + gb + ',' + gb + ',255)'},
        {adcode: "430000", color: 'rgb(' + gb + ',' + gb + ',255)'},
        {adcode: "440000", color: 'rgb(' + gb + ',' + gb + ',255)'},
        {adcode: "450000", color: 'rgb(' + gb + ',' + gb + ',255)'},
        {adcode: "460000", color: 'rgb(' + gb + ',' + gb + ',255)'},

        {adcode: "500000", color: 'rgb(' + gb + ',' + gb + ',255)'},
        {adcode: "510000", color: 'rgb(' + gb + ',' + gb + ',255)'},
        {adcode: "520000", color: 'rgb(' + gb + ',' + gb + ',255)'},
        {adcode: "530000", color: 'rgb(' + gb + ',' + gb + ',255)'},
        {adcode: "540000", color: 'rgb(' + gb + ',' + gb + ',255)'},

        {adcode: "610000", color: 'rgb(' + gb + ',' + gb + ',255)'},
        {adcode: "620000", color: 'rgb(' + gb + ',' + gb + ',255)'},
        {adcode: "630000", color: 'rgb(' + gb + ',' + gb + ',255)'},
        {adcode: "640000", color: 'rgb(' + gb + ',' + gb + ',255)'},
        {adcode: "650000", color: 'rgb(' + gb + ',' + gb + ',255)'},

        {adcode: "710000", color: 'rgb(' + gb + ',' + gb + ',255)'},
        {adcode: "810000", color: 'rgb(' + gb + ',' + gb + ',255)'},
        {adcode: "820000", color: 'rgb(' + gb + ',' + gb + ',255)'},];
      for (let a of adcodes) {

        let disProvince = new window.AMap.DistrictLayer.Province({
          zIndex: 12,
          adcode: a.adcode,
          depth: 0,
          styles: {
            'fill': a.color,
            'province-stroke': 'cornflowerblue',
            'city-stroke': 'white', // 中国地级市边界
            'county-stroke': 'rgba(255,255,255,0.5)' // 中国区县边界
          }
        });
        disProvince.setMap(map);
      }

    },
    drawLine() {
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
  mounted(){
    //DOM初始化完成进行地图初始化
    this.initMap();
  }
}
</script>



<style scoped>
#container{
  padding:0px;
  margin: 0px;
  width: 100%;
  height: 800px;
}
</style>