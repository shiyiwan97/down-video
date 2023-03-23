<template>
  <div id="background">
    <link rel="stylesheet" href="../assets/elementuistyle.css">
    <div id="start">

    </div>
    <div id="content1">
<!--      <button type="submit" onclick="submitData()">提交</button>-->
<!--      <button type="submit" id="hw">alert Hello World</button>-->
<!--      <button type="submit" id="downloadFile">alert Hello World</button>-->
      <el-input v-model="url" placeholder="输入要下载的网址" size="small"></el-input>
      <el-input v-model="seconds" placeholder="请输入下载视频的长度（单位：秒，可以多输不能少输）" size="small"></el-input>
      <el-button type="primary" @click="getVideo(url,seconds)" size="mini">添加<i
          class="el-icon-circle-plus"></i></el-button>

      <el-table :data="fileList">
        <el-table-column prop="fileName" label="文件名"></el-table-column>
        <el-table-column>
          <template slot-scope="scope">
            <el-button type="primary" @click="downloadFile(fileList[scope.$index].fileName)" size="mini">下载<i
                class="el-icon-download el-icon--right"></i></el-button>
          </template>
        </el-table-column>
      </el-table>

    </div>
    <div id="content2">

    </div>
    <test-components></test-components>
  </div>
</template>

<script>
/* eslint-disable */

import TestComponents from './TestComponents'
import axios from 'axios'

axios.defaults.baseURL = 'http://127.0.0.1:8081/videoDownload'

export default {
  name: 'VideoDownload',
  components: {TestComponents},
  data () {
    return {
      url:'',
      seconds:'',
      fileList: []
    }
  },
  mounted: function () {
    var that = this
    axios({
      url: 'listFile'
    }).then((res) => {
      this.fileList = res.data
    }).catch((error) => {
      alert(error)
    })
  },
  methods: {
    downloadFile (fileName) {
      const config = {
        method: 'get',
        url: 'getFile?fileName=' + fileName,
        headers: {
          // 和后端设置的一样
          'Content-Type': 'application/octet-stream;charset=UTF-8'
        },
        responseType: 'blob'
      }
      axios(config).then(response => {
        const url = window.URL.createObjectURL(new Blob([response.data]))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', fileName)
        document.body.appendChild(link)
        link.click()
      })
    },
    alert (s) {
      alert(s)
    },
    getVideo (url, seconds) {
      axios({
        url: 'getVideo',
        params: {
          url: url,
          seconds: seconds
        }
      }).then((ret) => {
        alert(ret)
      }).catch((error) => {
        alert(error)
      })
    }

  }
}
window.onload = function () {
  document.querySelector('#downloadFile').addEventListener('click', function () {
        const config = {
          method: 'get',
          url: 'getFile?fileName=古筝.txt',
          headers: {
            // 和后端设置的一样
            'Content-Type': 'application/octet-stream;charset=UTF-8'
          },
          responseType: 'blob'
        }
        axios(config).then(response => {
          const url = window.URL.createObjectURL(new Blob([response.data]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', '古筝.txt')
          document.body.appendChild(link)
          link.click()
        })
      }
  )
}

function bindbtnget (selectors, url, params) {
  document.querySelector(selectors).addEventListener('click', function () {
        axios({
          url: url,
          params: params
        }).then((ret) => {
          alert(ret)
        }).catch((error) => {
          alert(error)
        })
      }
  )
}

function getFileList () {
  axios({
    url: 'listFile'
  }).then((res) => {
    alert(res)
    this.fileNameList = res
  }).catch((error) => {

    alert(error + 123)
  })
}


</script>

<style scoped>

</style>
