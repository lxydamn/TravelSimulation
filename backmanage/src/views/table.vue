<template>
	<div>
		<div class="container">

      <el-button
          class="m-2"
          @click="popVisible = !popVisible"
          style="float: right;margin-bottom: 10px"
      >添加城市</el-button>

			<el-table
          :data="tableData"
          :cell-style="renderCell"
          border class="table"
          ref="multipleTable"
          header-cell-class-name="table-header"
          style="height:500px;"
      >
				<el-table-column prop="cityAdcode" label="城市Adcode" width="150" align="center"></el-table-column>
				<el-table-column prop="cityName" label="城市名称" align="center"></el-table-column>
				<el-table-column prop="grade" label="风险等级" align="center"></el-table-column>
				<el-table-column label="操作" width="220" align="center">
					<template #default="scope">
						<el-button text :icon="Edit" @click="handleEdit(scope.$index, scope.row)">
							修改
						</el-button>
            <el-button text :icon="Delete" @click="handleDelete(scope.$index, scope.row)">
              删除
            </el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
<!--    删除确认框-->




<!--    添加城市框-->
    <el-dialog v-model="popVisible" title="添加城市" width="600">
      <el-form >
        <el-row :gutter="30">
          <el-col :span="12">
            <elui-china-area-dht v-model="added_city.city_adcode" :leave="2"></elui-china-area-dht>
          </el-col>
          <el-col :span="12">
            <el-select v-model="added_city.city_grade" placeholder="Select" style="width: 250px">
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="popVisible = false">取消</el-button>
        <el-button type="primary" @click="addCity">
          确认添加
        </el-button>
      </span>
      </template>
    </el-dialog>


		<!-- 编辑弹出框 -->
		<el-dialog title="修改城市风险等级" v-model="visible" width="27%">
			<el-form label-width="70px">
				<el-form-item label="城市名">
					<el-input v-model="form.city_name" disabled style="width: 250px"></el-input>
				</el-form-item>
				<el-form-item label="风险等级">
          <el-select v-model="form.city_grade" placeholder="Select" style="width: 250px">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            />
          </el-select>
				</el-form-item>
			</el-form>
			<template #footer>
				<span>
          <el-row>
            <el-col :span="12">
              <el-button @click="visible = false" style="width: 130px">取 消</el-button>
            </el-col>
            <el-col :span="12">
              <el-button type="primary" @click="modifyGrade" style="width: 130px;">确 定</el-button>
            </el-col>
          </el-row>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script setup lang="ts" name="basetable">
import { ref, reactive } from 'vue';
import { ElMessage,ElMessageBox  } from 'element-plus';
import {Edit, Delete} from '@element-plus/icons-vue';
import axios from "axios";
import {EluiChinaAreaDht} from "elui-china-area-dht";
const chinaData = new EluiChinaAreaDht.ChinaArea().chinaAreaflat;

interface TableItem {
	cityAdcode: number;
	cityName: string;
	provinceName: string;
  provinceAdcode:number;
  grade:string;
}

let popVisible = ref(false);
let visible = ref(false);

let added_city = reactive({
  city_adcode: '',
  city_grade: '低风险',
});

let form = reactive({
  city_name: '',
  city_grade: '',
  city_adcode:'',
});

let idx: number = -1;
// 获取表格数据
const tableData = ref<TableItem[]>([]);

const getData = () => {
  axios({
    url: "http://localhost:3000/api/back/getcities/",
    method: "GET",
    headers: {
      Authorization: "Bearer " + localStorage.getItem("jwt_token"),
    },
  }).then((resp) => {
    tableData.value = resp.data;
    for (let i = 0; i < tableData.value.length; i++) {
      tableData.value[i].grade = toGrade[resp.data[i].grade];
    }
  })

};

const handleDelete = (index:number, row:any) => {
  ElMessageBox.confirm(
      '你确认要删除吗？',
      'warning',
      {
        confirmButtonText:'确认',
        cancelButtonText:'取消',
        type:'warning',
      }
  ).then(() => {
      let adcode = row.cityAdcode;

      axios({
        url:'http://localhost:3000/api/back/deletecity/',
        method:"POST",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("jwt_token"),
        },
        params:{
          adcode:adcode,
        },
      }).then((resp) => {
        if(resp.data.error_message === 'success') {
          ElMessage({
            type:'success',
            message:'删除成功',
          });

          getData();
        } else {
          ElMessage({
            type:'error',
            message:'删除失败',
          });
        }
      });

  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '取消删除',
    })
  })


}

const addCity = () => {

  popVisible.value = false;


  let name = chinaData[added_city.city_adcode[1]].label;
  let grade = added_city.city_adcode;


  if(grade === '低风险') grade = "0";
  else if(grade === '中风险') grade = "1";
  else grade = "2";

   axios({
    url:"http://localhost:3000/api/back/addcity/",
    method:"POST",
    headers: {
      Authorization: "Bearer " + localStorage.getItem("jwt_token"),
    },
    params: {
      adcode:added_city.city_adcode[1],
      name:name,
      grade: grade,
    }
  }).then((resp) => {
    if(resp.data.error_message != "success") {
      ElMessage({
        showClose:true,
        message: "添加失败！重复添加",
        type: "error",
      });
    } else {
      ElMessage({
        showClose:true,
        message: "添加成功",
        type: "success",
      });

      getData();
    }
  })

};

const renderCell = (row : any) => {

  if (row.row.grade === '中风险' && row.columnIndex === 2) {

    return {
      color:'#f86a6f',
    };
  } else if (row.row.grade === '高风险' && row.columnIndex === 2) {

    return {
      color:'#ff0000',
    };
  }
};


getData();


const toGrade = ['低风险', '中风险', '高风险'];
const options = [
  {
    value: 0,
    label:'低风险',
  },{
    value: 1,
    label: '中风险',
  },{
    value: 2,
    label: '高风险',
  }
]

// 修改风险等级
const modifyGrade = () => {
  visible.value = false;


  axios({
    url:"http://localhost:3000/api/back/modifycity/",
    method:"POST",
    headers: {
      Authorization: "Bearer " + localStorage.getItem("jwt_token"),
    },
    params:{
      adcode:form.city_adcode,
      grade:form.city_grade,
    },
  });
  // 表中显示修改
  for(let city of tableData.value) {
    if(city.cityAdcode === parseInt(form.city_adcode)) {
      city.grade = toGrade[parseInt(form.city_grade)];
    }
  }

  ElMessage({
    message:'修改成功',
    type:'success',
  })
}



const handleEdit = (index: number, row: any) => {
  visible.value = true;
	idx = index;
	form.city_name = row.cityName;
	form.city_grade = row.grade;
  form.city_adcode = row.cityAdcode;
};


</script>

<style scoped>

.table {
	width: 100%;
	font-size: 14px;
}

</style>
