node{
    //curl "http://<USER:PASSWORD>@192.168.198.128:8081/job/springCloud/buildWithParameters?token=<TOKEN>&module=<MODULE>"

    stage('更新代码') {
        checkout scm
    }

    stage('编译代码') {
        withEnv(["MVN_HOME=/var/jenkins_home/tools/apache-maven-3.6.1"]) {
            sh '"$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean package'
        }
    }

    stage('构建镜像') {
        // 安装Pipeline Utility Steps插件
        def pom = readMavenPom file: "${module}/pom.xml"
        dir (module) {
            def customImage = docker.build("registry.cn-hangzhou.aliyuncs.com/weilus923/${pom.artifactId}:${pom.version}")
            docker.withRegistry('https://registry.cn-hangzhou.aliyuncs.com/', 'registry.cn-hangzhou.aliyuncs.com') {
                customImage.push()
            }
        }
    }

    stage('镜像部署') {
        sh "kubectl apply -f ${module}/k8s.yaml"
    }

}
