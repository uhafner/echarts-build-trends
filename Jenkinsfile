node {
    def mvnHome = tool 'mvn-default'

    stage ('Checkout') {
        git branch:'master', url: 'https://github.com/uhafner/echarts-build-trends.git'
    }

    stage ('Build and Static Analysis') {
        withMaven(maven: 'mvn-default', mavenLocalRepo: '/var/data/m2repository', mavenOpts: '-Xmx768m -Xms512m') {
            sh 'mvn -V -e clean verify -Dmaven.test.failure.ignore -Dgpg.skip'
        }

        recordIssues tools: [java(), javaDoc()], aggregatingResults: 'true', id: 'java', name: 'Java'
        recordIssues tool: errorProne(), healthy: 1, unhealthy: 20

        junit testResults: '**/target/*-reports/TEST-*.xml'

        recordIssues tools: [checkStyle(pattern: 'target/checkstyle-result.xml'),
            spotBugs(pattern: 'target/spotbugsXml.xml'),
            pmdParser(pattern: 'target/pmd.xml'),
            cpd(pattern: 'target/cpd.xml')],
            qualityGates: [[threshold: 1, type: 'TOTAL', unstable: true]]
        recordIssues tool: taskScanner(highTags:'FIXME', normalTags:'TODO',
                includePattern: '**/*.java', excludePattern: 'target/**/*')
    }

    stage ('Line and Branch Coverage') {
        withMaven(maven: 'mvn-default', mavenLocalRepo: '/var/data/m2repository', mavenOpts: '-Xmx768m -Xms512m') {
            sh "mvn -V -U -e jacoco:prepare-agent test jacoco:report -Dmaven.test.failure.ignore"
        }
        publishCoverage adapters: [jacocoAdapter('**/*/jacoco.xml')], sourceFileResolver: sourceFiles('STORE_ALL_BUILD')
    }

    stage ('Mutation Coverage') {
        withMaven(maven: 'mvn-default', mavenLocalRepo: '/var/data/m2repository', mavenOpts: '-Xmx768m -Xms512m') {
            sh "mvn org.pitest:pitest-maven:mutationCoverage"
        }
        step([$class: 'PitPublisher', mutationStatsFile: 'target/pit-reports/**/mutations.xml'])
    }

    stage ('Collect Maven Warnings') {
        recordIssues tool: mavenConsole()
    }
}
