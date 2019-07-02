node{
    //curl "http://<USER:PASSWORD>@192.168.198.128:8081/job/springCloud/buildWithParameters?token=<TOKEN>&module=<MODULE>"
    def modules = ['admin','auth','feign-hystrix','gateway','oauth-resource']
    def deploy_modules = []

    stage('更新代码') {
        sh 'git fetch'
        try {
            sh "git diff --quiet master origin/master ${modules[3]}"  //exit 0 表示不存在更新
        } catch (e) {
            deploy_modules.add(${modules[3]})
        }
//        steps {
//            script {
//                modules.each{
//                    try {
//                        sh "git diff --quiet master origin/master ${it}"  //exit 0 表示不存在更新
//                    } catch (e) {
//                        echo "${it}模块存在更新"
//                        deploy_modules.add(${it})
//                    }
//                }
//            }
//        }
        println deploy_modules.toString()
//        checkout scm
    }

//    stage('编译代码') {
//        sh "mvn -Dmaven.test.failure.ignore clean package"
//    }
//
//    stage('构建镜像') {
//        // 安装Pipeline Utility Steps插件
//        def pom = readMavenPom file: "${module}/pom.xml"
//        dir (module) {
//            def customImage = docker.build("registry.cn-hangzhou.aliyuncs.com/weilus923/${pom.artifactId}:${pom.version}")
//            docker.withRegistry('https://registry.cn-hangzhou.aliyuncs.com/', 'registry.cn-hangzhou.aliyuncs.com') {
//                customImage.push()
//            }
//        }
//    }
//
//    stage('镜像部署') {
//        sh "kubectl apply -f ${module}/k8s.yaml"
//    }

}
