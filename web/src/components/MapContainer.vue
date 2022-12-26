<template>
  <el-card class="box-card">
    <div id="container">
    </div>
  </el-card>

</template>

<script>
import AMapLoader from '@amap/amap-jsapi-loader';
import { shallowRef } from '@vue/reactivity';
import { onMounted } from 'vue';

export default {
  setup() {

    let map = shallowRef(null);

    const ininMap = () => {

      //安全密钥
      window._AMapSecurityConfig = { securityJsCode: '374ed8dd1dc99358268d7529ca09928c' };

      AMapLoader.load({
        key: "ee9e3b30465a61728e51d506defb1b2a",
        version: "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        AMapUI: {
          // 是否加载 AMapUI，缺省不加载
          version: "1.1", // AMapUI 缺省 1.1
          plugins: ["geo/DistrictExplorer"], // 需要加载的 AMapUI ui插件
        },
        plugins: ["AMap.DistrictLayer", "AMap.Scale", "AMap.ToolBar"],
        Loca: {
          // 是否加载 Loca， 缺省不加载
          version: "2.0.0", // Loca 版本，缺省 2.0.0
        },
      })
        .then((AMap) => {

          var colors = {};
          var GDPSpeed = {
            '520000': 1,//贵州
            '540000': 0,//西藏
            '530000': 1,//云南 
            '500000': 2,//重庆
            '360000': 1,//江西
            '340000': 2,//安徽
            '510000': 2,//四川
            '350000': 1,//福建
            '430000': 2,//湖南
            '420000': 4, //湖北
            '410000': 1,//河南
            '330000': 3,//浙江
            '640000': 1,//宁夏
            '650000': 0,//新疆
            '440000': 3,//广东
            '370000': 2,//山东
            '450000': 1,//广西
            '630000': 0,//青海
            '320000': 1,//江苏
            '140000': 1,//山西
            '460000': 1,// 海南
            '310000': 2,//上海
            '110000': 0, // 北京
            '130000': 1, // 河北
            '230000': 1, // 黑龙江
            '220000': 1,// 吉林
            '210000': 1, //辽宁
            '150000': 0,//内蒙古
            '120000': 5,// 天津
            '620000': 0,// 甘肃
            '610000': 0,// 陕西
            '710000': 3, //台湾
            '810000': 1, //香港
            '820000': 1 //澳门
          }
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


          map = new AMap.Map("container", {
            //设置地图容器id
            zoom: 4.4, //初始化地图级别
            mapStyle:
              "amap://styles/8389cc93e89d4cf61b6873b24befc228", //设置地图的显示样式
            center: [106.542725, 36.583360], //初始化地图中心点位置//
            layers: [
              disCountry,
              defaultLayer,
            ],
            features: ['bg', 'building', 'point'],
          });


          /*// 这里使用 AMapUI.DistrictExplorer 主要是为了渲染地图国境线的颜色
          window.AMapUI.load(['ui/geo/DistrictExplorer'], function (DistrictExplorer) {

            //创建一个实例
            var districtExplorer = window.districtExplorer = new DistrictExplorer({
              eventSupport: true, //打开事件支持
              map: map
            });

            districtExplorer.loadAreaNode(100000, function (error, areaNode) {
              if (error) {
                console.error(error);
                return;
              }
              //清除已有的绘制内容
              districtExplorer.clearFeaturePolygons();

              //绘制父区域
              districtExplorer.renderParentFeature(areaNode, {
                cursor: 'default',
                bubble: true,
                strokeColor: 'white',//'#9ea3ab', //线颜色
                strokeOpacity: 1, //线透明度
                strokeWeight: 2, //线宽
                fillOpacity: 0, //填充透明度
              });
            });
          });*/

        })
        .catch((e) => {
          console.log(e);
        });
    };
    onMounted(() => {
      ininMap();
    });
    return {
      map,
    };
  },
};
</script>



<style scoped>
#container {
  width: 100%;
  height: 650px;
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