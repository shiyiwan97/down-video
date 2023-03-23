import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import TestComponents from '@/components/TestComponents'
import VideoDownload from '@/components/VideoDownload'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello11111',
      component: VideoDownload
    },

    {
      path: '/TestComponents',
      name: 'TestComponents',
      component: TestComponents
    }
  ]
})

function a (a1, b1) {
  return 1
}

function b () {
  return 2
}

a(b(), a(b(), function () {
  return 123
}))
