node{
    //curl "http://<USER:PASSWORD>@192.168.198.128:8081/job/springCloud/build?token=<TOKEN>"
    def modules = ['admin','auth','feign-hystrix','gateway','oauth-resource']
    def deploy_modules = []

    stage('更新代码') {
        sh 'git fetch'
        for(module in modules){
            try {
                sh "git diff --quiet master origin/master "+module  //exit 0 表示不存在更新
            }
            catch (e) {
                deploy_modules.add(module)
            }
        }
        checkout scm
    }

    stage('编译代码') {
        sh "mvn -Dmaven.test.failure.ignore clean package"
    }

    stage('构建镜像') {
        for(module in deploy_modules) {
            def pom = readMavenPom file: "${module}/pom.xml"
            dir(module) {
                def customImage = docker.build("registry.cn-hangzhou.aliyuncs.com/weilus923/${pom.artifactId}:${pom.version}")
                docker.withRegistry('https://registry.cn-hangzhou.aliyuncs.com/', 'registry.cn-hangzhou.aliyuncs.com') {
                    customImage.push()
                }
            }
        }
    }

    stage('镜像部署') {
        for(module in deploy_modules) {
            sh "kubectl apply -f ${module}/k8s.yaml"
        }
    }

}
