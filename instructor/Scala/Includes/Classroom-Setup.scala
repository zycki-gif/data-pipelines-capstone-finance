{"commands": [{"bindings": {}, "collapsed": false, "command": "\n%python\n# We need the python version of financeDataPath in 03B - it has an ALL_NOTEBOOKS Python code which needs to be execute\n# There shouldn't be anything else in this cell that would vary between Python and Scala doing this to the whole cell should be OK\n\nfinanceDataPath = \"dbfs:/mnt/training/finance-org\"\n\nspark.conf.set(\"com.databricks.training.module-name\", \"capstone-finance\")\n\nspark.conf.set(\"com.databricks.training.suppress.untilStreamIsReady\", \"true\")\nspark.conf.set(\"com.databricks.training.suppress.stopAllStreams\", \"true\")\nspark.conf.set(\"com.databricks.training.suppress.moduleName\", \"true\")\nspark.conf.set(\"com.databricks.training.suppress.lessonName\", \"true\")\nspark.conf.set(\"com.databricks.training.suppress.username\", \"true\")\nspark.conf.set(\"com.databricks.training.suppress.userhome\", \"true\")\nspark.conf.set(\"com.databricks.training.suppress.workingDir\", \"true\")\n\ndisplayHTML(\"Preparing the learning environment...\")", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "18c43bb4-620b-47a5-9018-cb19e86ce6d3", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "3cca1611-017b-40f0-8551-afb76f410e52", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 1, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "val financeDataPath = \"dbfs:/mnt/training/finance-org\"\n\nspark.conf.set(\"com.databricks.training.module-name\", \"capstone-finance\")\n\nspark.conf.set(\"com.databricks.training.suppress.untilStreamIsReady\", \"true\")\nspark.conf.set(\"com.databricks.training.suppress.stopAllStreams\", \"true\")\nspark.conf.set(\"com.databricks.training.suppress.moduleName\", \"true\")\nspark.conf.set(\"com.databricks.training.suppress.lessonName\", \"true\")\nspark.conf.set(\"com.databricks.training.suppress.username\", \"true\")\nspark.conf.set(\"com.databricks.training.suppress.userhome\", \"true\")\nspark.conf.set(\"com.databricks.training.suppress.workingDir\", \"true\")\n\ndisplayHTML(\"Preparing the learning environment...\")", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "e5d83999-83ed-4064-9058-6bb12a618acf", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "058794ae-da1a-486a-9cee-5cf5fb9c7364", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 2, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "%run ./Common-Notebooks/Common", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "22b7d4a2-dd56-408a-96ed-dfc7e41ff71e", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "82ae87b7-27eb-4aa3-8d00-54087036ad1b", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 3, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}, {"bindings": {}, "collapsed": false, "command": "courseAdvertisements += (\"financeDataPath\" -> (\"v\", financeDataPath, \"The location of the finance dataset used in this capstone project.\"))\n\nval sourceDataPath = financeDataPath + \"/solutions\"\ncourseAdvertisements += (\"sourceDataPath\" -> (\"v\", sourceDataPath, \"The location of the specific datasets used in this capstone project as source data.\"))\n\ndef roundDataFrame(df:org.apache.spark.sql.DataFrame): org.apache.spark.sql.DataFrame = {\n  df.schema.filter(c =>\n    c.dataType == org.apache.spark.sql.types.DoubleType || c.dataType == org.apache.spark.sql.types.FloatType)\n  .foldRight(df){ (c, currDF) => currDF.withColumn(c.name, org.apache.spark.sql.functions.floor(org.apache.spark.sql.functions.col(c.name)))}\n}\n\ndef compareDataFramesLimited(df1:org.apache.spark.sql.DataFrame, df2:org.apache.spark.sql.DataFrame):Boolean = {\n  val sortedColumns1 = df1.columns.sorted\n  val h1 = sortedColumns1.head\n  val t1 = sortedColumns1.tail\n  val df1Sorted = df1.select(h1, t1:_*).orderBy(h1, t1:_*).limit(10000)\n\n  val sortedColumns2 = df2.columns.sorted\n  val h2 = sortedColumns2.head\n  val t2 = sortedColumns2.tail\n  val df2Sorted = df2.select(h2, t2:_*).orderBy(h2, t2:_*).limit(10000)\n  \n  return compareDataFrames(roundDataFrame(df1Sorted), roundDataFrame(df2Sorted), testColumnOrder=true, testNullable=false)\n}\n\ndisplayHTML(\"Finalizing the learning environment...\")\n", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "577b9bb4-6a7f-419d-86bb-e8eb232d846d", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "0af73c65-175f-44eb-b7f6-1cd21db072e6", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 4, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}], "dashboards": [], "globalVars": {}, "guid": "d38ee723-7bad-4260-9f68-aa152c1004ea", "iPythonMetadata": null, "inputWidgets": {}, "language": "scala", "name": "Classroom-Setup", "origId": 0, "version": "NotebookV1"}