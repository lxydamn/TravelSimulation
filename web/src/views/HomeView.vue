<template>
  <el-container>
    <el-main>
      <el-row>
        <el-col :span="16">
          <map-container>
          </map-container>
        </el-col>

        <el-col :span="8">
          <el-card class="box-card">
            <el-row>
              <el-col :span="10">
                <div class="box">
                  <div>出发地</div>
                  <el-select placeholder="please select your zone" style="margin-top: 15px">
                    <el-option label="Zone one" value="shanghai" />
                    <el-option label="Zone two" value="beijing" />
                  </el-select>
                </div>
              </el-col>
              <el-col :span="4">
                <div class="box">
                  <svg xmlns="http://www.w3.org/2000/svg" style="margin-top: 40px; margin-left: 20px;" width="25"
                    height="30" fill="currentColor" class="bi bi-arrow-right" viewBox="0 0 16 16">
                    <path fill-rule="evenodd"
                      d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8z" />
                  </svg>
                </div>
              </el-col>
              <el-col :span="10">
                <div class="box">
                  <div>目的地</div>
                  <el-select placeholder="please select your zone" style="margin-top: 15px">
                    <el-option label="Zone one" value="shanghai" />
                    <el-option label="Zone two" value="beijing" />
                  </el-select>
                </div>
              </el-col>
            </el-row>

            <div class="box">
              <el-row style="margin-top: 10px;">
                <div style="margin-top: 5px;">交通工具：</div>
                <div>
                  <el-checkbox label="汽车" size="large" style="margin-left: 32px;" />
                  <el-checkbox label="火车" size="large" />
                  <el-checkbox label="飞机" size="large" />
                </div>
              </el-row>
            </div>

            <el-row style="margin-top: 10px;">
              <el-col :span="6">
                <div>出发日期: </div>
              </el-col>
              <el-col :span="18">
                <el-date-picker type="date" placeholder="Pick a day" :size="size" />
              </el-col>
            </el-row>

            <el-row style="margin-top: 10px;">
              <el-col :span="6">
                <div>出行策略: </div>
              </el-col>
              <el-col :span="18">
                <el-select v-model="value2" placeholder="Select">
                  <el-option v-for="item in options1" :key="item.value" :label="item.label" :value="item.value"
                    :disabled="item.disabled" />
                </el-select>
              </el-col>
            </el-row>

            <el-row style="margin-top: 10px;">
              <el-col :span="6">
                <div>出行策略: </div>
              </el-col>
              <el-col :span="18">
                <el-select-v2 v-model="value" :options="options" placeholder="Please select" style="width: 240px"
                  multiple />
              </el-col>
            </el-row>

            <el-row style="margin-top: 20px; margin-bottom: 20px; float: right;">
              <el-button>规划路径</el-button>
            </el-row>


          </el-card>



          <el-card class="box-card" style="margin-top: 20px; height: 48%;">
            <el-button text @click="dialogFormVisible = true">
              open a Form nested Dialog
            </el-button>

            <el-dialog v-model="dialogFormVisible" title="Shipping address">
              <el-form :model="form">
                <el-form-item label="Promotion name" :label-width="formLabelWidth">
                  <el-input v-model="form.name" autocomplete="off" />
                </el-form-item>
                <el-form-item label="Zones" :label-width="formLabelWidth">
                  <el-select v-model="form.region" placeholder="Please select a zone">
                    <el-option label="Zone No.1" value="shanghai" />
                    <el-option label="Zone No.2" value="beijing" />
                  </el-select>
                </el-form-item>
              </el-form>
              <template #footer>
                <span class="dialog-footer">
                  <el-button @click="dialogFormVisible = false">Cancel</el-button>
                  <el-button type="primary" @click="dialogFormVisible = false">
                    Confirm
                  </el-button>
                </span>
              </template>
            </el-dialog>

          </el-card>
        </el-col>

      </el-row>
    </el-main>
  </el-container>

</template>
<script>
import MapContainer from "@/components/MapContainer";
import { reactive, ref } from 'vue'

export default {
  components: {
    MapContainer,
  },
  setup() {
    const initials = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j']
    let value1 = ref('')
    let value = ref([])
    let options = ref(
      Array.from({ length: 1000 }).map((_, idx) => ({
        value: `Option ${idx + 1}`,
        label: `${initials[idx % 10]}${idx}`,
      }))
    )

    const dialogTableVisible = ref(false)
    const dialogFormVisible = ref(false)
    const formLabelWidth = '140px'

    const form = reactive({
      name: '',
      region: '',
      date1: '',
      date2: '',
      delivery: false,
      type: [],
      resource: '',
      desc: '',
    })

    const value2 = ref('')
    const options1 = [
      {
        value: 'Option1',
        label: 'Option1',
      },
      {
        value: 'Option2',
        label: 'Option2',
        disabled: true,
      },
      {
        value: 'Option3',
        label: 'Option3',
      },
      {
        value: 'Option4',
        label: 'Option4',
      },
      {
        value: 'Option5',
        label: 'Option5',
      },
    ]
    return {
      value1,
      options,
      value,
      dialogTableVisible,
      dialogFormVisible,
      formLabelWidth,
      form,
      value2,
      options1
    }
  }
}
</script>

<style scoped>

</style>