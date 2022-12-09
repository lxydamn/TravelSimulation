<template>
	<div>
		<div class="container">

			<el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
				<el-table-column prop="cityAdcode" label="城市Adcode" width="150" align="center"></el-table-column>
				<el-table-column prop="cityName" label="城市名称" align="center"></el-table-column>
				<el-table-column prop="grade" label="风险等级" align="center"></el-table-column>
				<el-table-column label="操作" width="220" align="center">
					<template #default="scope">
						<el-button text :icon="Edit" @click="handleEdit(scope.$index, scope.row)">
							修改
						</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>

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
import { ElMessage } from 'element-plus';
import { Delete, Edit, Search} from '@element-plus/icons-vue';
import axios from "axios";

interface TableItem {
	cityAdcode: number;
	cityName: string;
	provinceName: string;
  provinceAdcode:number;
  grade:string;
}

let visible = ref(false);
const tableData = ref<TableItem[]>([]);
// 获取表格数据
const getData = () => {
	axios({
    url: "http://localhost:3000/api/back/getcities/",
    method: "GET",
    headers: {
      Authorization: "Bearer " + localStorage.getItem("jwt_token"),
    },
  }).then((resp) => {
      tableData.value = resp.data;
      for(let i = 0; i < tableData.value.length; i ++) {
          tableData.value[i].grade = toGrade[resp.data[i].grade];
      }
  })

};

const toGrade = ['低风险','中风险', '高风险'];
getData();
// 风险地选择器
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


let form = reactive({
	city_name: '',
	city_grade: '',
  city_adcode:'',
});

let idx: number = -1;

const handleEdit = (index: number, row: any) => {
  visible.value = true;
	idx = index;
	form.city_name = row.cityName;
	form.city_grade = row.grade;
  form.city_adcode = row.cityAdcode;
};

const saveEdit = () => {
	ElMessage.success();
};

</script>

<style scoped>

.table {
	width: 100%;
	font-size: 14px;
}

</style>
