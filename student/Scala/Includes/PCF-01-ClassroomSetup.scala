{"commands": [{"bindings": {}, "collapsed": false, "command": "\n%run ./Classroom-Setup", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "0cf1bc4c-4a65-44ce-abd9-2f3264de0fea", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "4001a613-f06c-4a91-a3f9-9cd93219126f", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 1, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "val companyEmployeesPath = financeDataPath + \"/employees/employees.csv\"\nval assetsPath = financeDataPath + \"/assets/assets.csv\"\n\ndef realityCheck(testMethod:(String)=>Long): Unit = {\n  val companyEmployeesPath = financeDataPath + \"/employees/employees.csv\"\n  val actEmployeeCount = testMethod(companyEmployeesPath)\n  val expEmployeeCount = spark.read.csv(companyEmployeesPath).count()\n  \n  val actAssetCount = testMethod(assetsPath)\n  val expAssetCount = spark.read.csv(assetsPath).count()\n  \n  val tests = TestSuite()\n  tests.testEquals(\"PCF-01-A\", s\"Expected <b>${expEmployeeCount}</b> records from company_employees.csv, found <b>${actEmployeeCount}</b>\", expEmployeeCount, actEmployeeCount)\n  tests.testEquals(\"PCF-01-B\", s\"Expected <b>${expAssetCount}</b> records from assets.csv, found <b>${actAssetCount}</b>\", expAssetCount, actAssetCount)\n  tests.displayResults()\n}\n\ncourseAdvertisements += (\"companyEmployeesPath\" -> (\"v\", companyEmployeesPath, \"The path to a CSV file to be used in this lesson.\"))\ncourseAdvertisements += (\"assetsPath\" -> (\"v\", assetsPath, \"The path to a CSV file to be used in this lesson.\"))\ncourseAdvertisements += (\"realityCheck\" ->         (\"f\", \"func\",               \"The reality-check function used to evaluate your solution.\"))\n\nallDone(courseAdvertisements)", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "30c51e61-34c6-4bd3-8938-6d7aea0ae1cd", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "6cfcd7a5-ddbe-4a6d-9430-e2644b809f5a", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 2, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "96272ddc-fd3f-4e1f-89e6-6ed1a07da156", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "dab7d852-e812-415b-8470-8ed2883b7e7c", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 3, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}], "dashboards": [], "globalVars": {}, "guid": "b1792509-4402-4973-9239-6934d40ffbad", "iPythonMetadata": null, "inputWidgets": {}, "language": "scala", "name": "PCF-01-ClassroomSetup", "origId": 0, "version": "NotebookV1"}