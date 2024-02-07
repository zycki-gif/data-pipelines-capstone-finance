{"commands": [{"bindings": {}, "collapsed": false, "command": "\n//*******************************************\n// TAG API FUNCTIONS\n//*******************************************\n\n// Get all tags\ndef getTags(): Map[com.databricks.logging.TagDefinition,String] = {\n  com.databricks.logging.AttributionContext.current.tags\n} \n\n// Get a single tag's value\ndef getTag(tagName: String, defaultValue: String = null): String = {\n  val values = getTags().collect({ case (t, v) if t.name == tagName => v }).toSeq\n  values.size match {\n    case 0 => defaultValue\n    case _ => values.head.toString\n  }\n}\n\n//*******************************************\n// Get Databricks runtime major and minor versions\n//*******************************************\n\ndef getDbrMajorAndMinorVersions(): (Int, Int) = {\n  val dbrVersion = System.getenv().get(\"DATABRICKS_RUNTIME_VERSION\")\n  val Array(dbrMajorVersion, dbrMinorVersion, _*) = dbrVersion.split(\"\"\"\\.\"\"\")\n  return (dbrMajorVersion.toInt, dbrMinorVersion.toInt)\n}\n\n\n//*******************************************\n// USER, USERNAME, AND USERHOME FUNCTIONS\n//*******************************************\n\n// Get the user's username\ndef getUsername(): String = {\n  return try {\n    dbutils.widgets.get(\"databricksUsername\")\n  } catch {\n    case _: Exception => getTag(\"user\", java.util.UUID.randomUUID.toString.replace(\"-\", \"\"))\n  }\n}\n\n// Get the user's userhome\ndef getUserhome(): String = {\n  val username = getUsername()\n  return s\"dbfs:/user/$username\"\n}\n\ndef getModuleName(): String = {\n  // This will/should fail if module-name is not defined in the Classroom-Setup notebook\n  return spark.conf.get(\"com.databricks.training.module-name\")\n}\n\ndef getLessonName(): String = {\n  // If not specified, use the notebook's name.\n  return dbutils.notebook.getContext.notebookPath.get.split(\"/\").last\n}\n\ndef getWorkingDir(courseType:String): String = {\n  val langType = \"s\" // for scala\n  val moduleName = getModuleName().replaceAll(\"[^a-zA-Z0-9]\", \"_\").toLowerCase()\n  val lessonName = getLessonName().replaceAll(\"[^a-zA-Z0-9]\", \"_\").toLowerCase()\n  val workingDir = \"%s/%s/%s_%s%s\".format(getUserhome(), moduleName, lessonName, langType, courseType)\n  return workingDir.replace(\"__\", \"_\").replace(\"__\", \"_\").replace(\"__\", \"_\").replace(\"__\", \"_\")\n}\n\n//**********************************\n// VERSION ASSERTION FUNCTIONS\n//**********************************\n\n// When migrating DBR versions this should be one\n// of the only two places that needs to be updated\n\nval latestDbrMajor = 7\nval latestDbrMinor = 0\n\n// Assert an appropriate Databricks Runtime version\ndef assertDbrVersion(expected:String, latestMajor:Int = latestDbrMajor, latestMinor:Int = latestDbrMinor, display:Boolean=true):String = {\n  \n  var expMajor = latestMajor\n  var expMinor = latestMinor\n  \n  if (expected != null && expected.trim() != \"\" && expected != \"{{dbr}}\") {\n    expMajor = expected.split('.')(0).toInt\n    expMinor = expected.split('.')(1).toInt\n  }\n  \n  val (major, minor) = getDbrMajorAndMinorVersions()\n\n  if ((major < expMajor) || (major == expMajor && minor < expMinor)) {\n    throw new AssertionError(s\"This notebook must be run on DBR $expMajor.$expMinor or newer. Your cluster is using $major.$minor. You must update your cluster configuration before proceeding.\")\n  }\n  \n  val html = if (major != expMajor || minor != expMinor) {\n    s\"\"\"\n      <div style=\"color:red; font-weight:bold\">WARNING: This notebook was tested on DBR $expMajor.$expMinor but we found DBR $major.$minor.</div>\n      <div style=\"font-weight:bold\">Using an untested DBR may yield unexpected results and/or various errors</div>\n      <div style=\"font-weight:bold\">Please update your cluster configuration and/or <a href=\"https://academy.databricks.com/\" target=\"_blank\">download a newer version of this course</a> before proceeding.</div>\n    \"\"\"\n  } else {\n    s\"Running on <b>DBR $major.$minor</b>\"\n  }\n\n  if (display) \n    displayHTML(html) \n  else \n    println(html)\n  \n  return s\"$major.$minor\"\n}\n\n// Assert that the Databricks Runtime is an ML Runtime\n// def assertIsMlRuntime(testValue:String = null):Unit = {\n  \n//   val sourceValue = if (testValue != null) testValue\n//   else getRuntimeVersion()\n\n//   if (sourceValue.contains(\"-ml-\") == false) {\n//     throw new AssertionError(s\"This notebook must be ran on a Databricks ML Runtime, found $sourceValue.\")\n//   }\n// }\n\n\n//**********************************\n// LEGACY TESTING FUNCTIONS\n//********************************getDbrMajorAndMinorVersions**\n\nimport org.apache.spark.sql.DataFrame\n\n// Test results map to store results\nval testResults = scala.collection.mutable.Map[String, (Boolean, String)]()\n\n// Hash a string value\ndef toHash(value:String):Int = {\n  import org.apache.spark.sql.functions.hash\n  import org.apache.spark.sql.functions.abs\n  spark.createDataset(List(value)).select(abs(hash($\"value\")).cast(\"int\")).as[Int].first()\n}\n\n// Clear the testResults map\ndef clearYourResults(passedOnly:Boolean = true):Unit = {\n  val whats = testResults.keySet.toSeq.sorted\n  for (what <- whats) {\n    val passed = testResults(what)._1\n    if (passed || passedOnly == false) testResults.remove(what)\n  }\n}\n\n// Validate DataFrame schema\ndef validateYourSchema(what:String, df:DataFrame, expColumnName:String, expColumnType:String = null):Unit = {\n  val label = s\"$expColumnName:$expColumnType\"\n  val key = s\"$what contains $label\"\n  \n  try{\n    val actualTypeTemp = df.schema(expColumnName).dataType.typeName\n    val actualType = if (actualTypeTemp.startsWith(\"decimal\")) \"decimal\" else actualTypeTemp\n    \n    if (expColumnType == null) {\n      testResults.put(key,(true, \"validated\"))\n      println(s\"\"\"$key: validated\"\"\")\n      \n    } else if (actualType == expColumnType) {\n      val answerStr = \"%s:%s\".format(expColumnName, actualType)\n      testResults.put(key,(true, \"validated\"))\n      println(s\"\"\"$key: validated\"\"\")\n      \n    } else {\n      val answerStr = \"%s:%s\".format(expColumnName, actualType)\n      testResults.put(key,(false, answerStr))\n      println(s\"\"\"$key: NOT matching ($answerStr)\"\"\")\n    }\n  } catch {\n    case e:java.lang.IllegalArgumentException => {\n      testResults.put(key,(false, \"-not found-\"))\n      println(s\"$key: NOT found\")\n    }\n  }\n}\n\n// Validate an answer\ndef validateYourAnswer(what:String, expectedHash:Int, answer:Any):Unit = {\n  // Convert the value to string, remove new lines and carriage returns and then escape quotes\n  val answerStr = if (answer == null) \"null\" \n  else answer.toString\n\n  val hashValue = toHash(answerStr)\n\n  if (hashValue == expectedHash) {\n    testResults.put(what,(true, answerStr))\n    println(s\"\"\"$what was correct, your answer: ${answerStr}\"\"\")\n  } else{\n    testResults.put(what,(false, answerStr))\n    println(s\"\"\"$what was NOT correct, your answer: ${answerStr}\"\"\")\n  }\n}\n\n// Summarize results in the testResults function\ndef summarizeYourResults():Unit = {\n  var html = \"\"\"<html><body><div style=\"font-weight:bold; font-size:larger; border-bottom: 1px solid #f0f0f0\">Your Answers</div><table style='margin:0'>\"\"\"\n\n  val whats = testResults.keySet.toSeq.sorted\n  for (what <- whats) {\n    val passed = testResults(what)._1\n    val answer = testResults(what)._2\n    val color = if (passed) \"green\" else \"red\" \n    val passFail = if (passed) \"passed\" else \"FAILED\" \n    html += s\"\"\"<tr style='font-size:larger; white-space:pre'>\n                  <td>${what}:&nbsp;&nbsp;</td>\n                  <td style=\"color:${color}; text-align:center; font-weight:bold\">${passFail}</td>\n                  <td style=\"white-space:pre; font-family: monospace\">&nbsp;&nbsp;${answer}</td>\n                </tr>\"\"\"\n  }\n  html += \"</table></body></html>\"\n  displayHTML(html)\n}\n\n// Log test results to a file\ndef logYourTest(path:String, name:String, value:Double):Unit = {\n  if (path.contains(\"\\\"\")) throw new AssertionError(\"The name cannot contain quotes.\")\n  \n  dbutils.fs.mkdirs(path)\n\n  val csv = \"\"\" \"%s\",\"%s\" \"\"\".format(name, value).trim()\n  val file = \"%s/%s.csv\".format(path, name).replace(\" \", \"-\").toLowerCase\n  dbutils.fs.put(file, csv, true)\n}\n\n// Load the test results log file\ndef loadYourTestResults(path:String):org.apache.spark.sql.DataFrame = {\n  return spark.read.schema(\"name string, value double\").csv(path)\n}\n\n// Load the test result log file into map\ndef loadYourTestMap(path:String):scala.collection.mutable.Map[String,Double] = {\n  case class TestResult(name:String, value:Double)\n  val rows = loadYourTestResults(path).collect()\n  \n  val map = scala.collection.mutable.Map[String,Double]()\n  for (row <- rows) map.put(row.getString(0), row.getDouble(1))\n  \n  return map\n}\n\n//**********************************\n// USER DATABASE FUNCTIONS\n//**********************************\n\ndef getDatabaseName(courseType:String, username:String, moduleName:String, lessonName:String):String = {\n  val langType = \"s\" // for scala\n  var databaseName = username + \"_\" + moduleName + \"_\" + lessonName + \"_\" + langType + courseType\n  databaseName = databaseName.toLowerCase\n  databaseName = databaseName.replaceAll(\"[^a-zA-Z0-9]\", \"_\")\n  return databaseName.replace(\"__\", \"_\").replace(\"__\", \"_\").replace(\"__\", \"_\").replace(\"__\", \"_\")\n}\n\n// Create a user-specific database\ndef createUserDatabase(courseType:String, username:String, moduleName:String, lessonName:String):String = {\n  val databaseName = getDatabaseName(courseType, username, moduleName, lessonName)\n\n  spark.sql(\"CREATE DATABASE IF NOT EXISTS %s\".format(databaseName))\n  spark.sql(\"USE %s\".format(databaseName))\n\n  return databaseName\n}\n\n//**********************************\n// ML FLOW UTILITY FUNCTIONS\n//**********************************\n\n// Create the experiment ID from available notebook information\ndef getExperimentId(): Long = {\n  val notebookId = getTag(\"notebookId\", null)\n  val jobId = getTag(\"jobId\", null)\n  \n  \n  val retval = (notebookId != null) match { \n      case true => notebookId.toLong\n      case false => (jobId != null) match { \n        case true => jobId.toLong\n        case false => 0\n      }\n  }\n  \n  spark.conf.set(\"com.databricks.training.experimentId\", retval)\n  retval\n}\n\n// ****************************************************************************\n// Utility method to determine whether a path exists\n// ****************************************************************************\n\ndef pathExists(path:String):Boolean = {\n  try {\n    dbutils.fs.ls(path)\n    return true\n  } catch{\n    case e: Exception => return false\n  } \n}\n\n// ****************************************************************************\n// Utility method for recursive deletes\n// Note: dbutils.fs.rm() does not appear to be truely recursive\n// ****************************************************************************\n\ndef deletePath(path:String):Unit = {\n  val files = dbutils.fs.ls(path)\n\n  for (file <- files) {\n    val deleted = dbutils.fs.rm(file.path, true)\n    \n    if (deleted == false) {\n      if (file.isDir) {\n        deletePath(file.path)\n      } else {\n        throw new java.io.IOException(\"Unable to delete file: \" + file.path)\n      }\n    }\n  }\n  \n  if (dbutils.fs.rm(path, true) == false) {\n    throw new java.io.IOException(\"Unable to delete directory: \" + path)\n  }\n}\n\n// ****************************************************************************\n// Utility method to clean up the workspace at the end of a lesson\n// ****************************************************************************\n\ndef classroomCleanup(daLogger:DatabricksAcademyLogger, courseType:String, username:String, moduleName:String, lessonName:String, dropDatabase: Boolean):Unit = {\n  \n  var actions = \"\"\n\n  // Stop any active streams\n  for (stream <- spark.streams.active) {\n    stream.stop()\n    \n    // Wait for the stream to stop\n    var queries = spark.streams.active.filter(_.name == stream.name)\n\n    while (queries.length > 0) {\n      Thread.sleep(5*1000) // Give it a couple of seconds\n      queries = spark.streams.active.filter(_.name == stream.name)\n    }\n\n    actions += s\"\"\"<li>Terminated stream: <b>${stream.name}</b></li>\"\"\"\n  }\n  \n  // Drop the tables only from specified database\n  val database = getDatabaseName(courseType, username, moduleName, lessonName)\n  try {\n    val tables = spark.sql(s\"show tables from $database\").select(\"tableName\").collect()\n    for (row <- tables){\n      var tableName = row.getAs[String](\"tableName\")\n      spark.sql(\"drop table if exists %s.%s\".format(database, tableName))\n\n      // In some rare cases the files don't actually get removed.\n      Thread.sleep(1000) // Give it just a second...\n      val hivePath = \"dbfs:/user/hive/warehouse/%s.db/%s\".format(database, tableName)\n      dbutils.fs.rm(hivePath, true) // Ignoring the delete's success or failure\n      \n      actions += s\"\"\"<li>Dropped table: <b>${tableName}</b></li>\"\"\"\n    } \n  } catch {\n    case _: Exception => () // ignored\n  }\n\n  // Drop the database if instructed to\n  if (dropDatabase){\n    spark.sql(s\"DROP DATABASE IF EXISTS $database CASCADE\")\n\n    // In some rare cases the files don't actually get removed.\n    Thread.sleep(1000) // Give it just a second...\n    val hivePath = \"dbfs:/user/hive/warehouse/%s.db\".format(database)\n    dbutils.fs.rm(hivePath, true) // Ignoring the delete's success or failure\n    \n    actions += s\"\"\"<li>Dropped database: <b>${database}</b></li>\"\"\"\n  }\n  \n  // Remove files created from previous runs\n  val path = getWorkingDir(courseType)\n  if (pathExists(path)) {\n    deletePath(path)\n\n    actions += s\"\"\"<li>Removed working directory: <b>$path</b></li>\"\"\"\n  }\n  \n  var htmlMsg = \"Cleaning up the learning environment...\"\n  if (actions.length == 0) htmlMsg += \"no actions taken.\"\n  else htmlMsg += s\"<ul>$actions</ul>\"\n  displayHTML(htmlMsg)\n  \n  if (dropDatabase) daLogger.logEvent(\"Classroom-Cleanup-Final\")\n  else daLogger.logEvent(\"Classroom-Cleanup-Preliminary\")\n}\n\n// ****************************************************************************\n// Utility method to delete a database\n// ****************************************************************************\n\ndef deleteTables(database:String):Unit = {\n  spark.sql(\"DROP DATABASE IF EXISTS %s CASCADE\".format(database))\n}\n\n// ****************************************************************************\n// DatabricksAcademyLogger and Student Feedback\n// ****************************************************************************\n\nclass DatabricksAcademyLogger() extends org.apache.spark.scheduler.SparkListener {\n  import org.apache.spark.scheduler._\n\n  val hostname = \"https://rqbr3jqop0.execute-api.us-west-2.amazonaws.com/prod\"\n\n  def logEvent(eventId: String, message: String = null):Unit = {\n    import org.apache.http.entity._\n    import org.apache.http.impl.client.{HttpClients, BasicResponseHandler}\n    import org.apache.http.client.methods.HttpPost\n    import java.net.URLEncoder.encode\n    import org.json4s.jackson.Serialization\n    implicit val formats = org.json4s.DefaultFormats\n\n    val start = System.currentTimeMillis // Start the clock\n\n    var client:org.apache.http.impl.client.CloseableHttpClient = null\n\n    try {\n      val utf8 = java.nio.charset.StandardCharsets.UTF_8.toString;\n      val username = encode(getUsername(), utf8)\n      val moduleName = encode(getModuleName(), utf8)\n      val lessonName = encode(getLessonName(), utf8)\n      val event = encode(eventId, utf8)\n      val url = s\"$hostname/logger\"\n    \n      val content = Map(\n        \"tags\" ->       getTags().map(x => (x._1.name, s\"${x._2}\")),\n        \"moduleName\" -> getModuleName(),\n        \"lessonName\" -> getLessonName(),\n        \"orgId\" ->      getTag(\"orgId\", \"unknown\"),\n        \"username\" ->   getUsername(),\n        \"eventId\" ->    eventId,\n        \"eventTime\" ->  s\"${System.currentTimeMillis}\",\n        \"language\" ->   getTag(\"notebookLanguage\", \"unknown\"),\n        \"notebookId\" -> getTag(\"notebookId\", \"unknown\"),\n        \"sessionId\" ->  getTag(\"sessionId\", \"unknown\"),\n        \"message\" ->    message\n      )\n      \n      val client = HttpClients.createDefault()\n      val httpPost = new HttpPost(url)\n      val entity = new StringEntity(Serialization.write(content))      \n\n      httpPost.setEntity(entity)\n      httpPost.setHeader(\"Accept\", \"application/json\")\n      httpPost.setHeader(\"Content-type\", \"application/json\")\n\n      val response = client.execute(httpPost)\n      \n      val duration = System.currentTimeMillis - start // Stop the clock\n      org.apache.log4j.Logger.getLogger(getClass).info(s\"DAL-$eventId-SUCCESS: Event completed in $duration ms\")\n      \n    } catch {\n      case e:Exception => {\n        val duration = System.currentTimeMillis - start // Stop the clock\n        val msg = s\"DAL-$eventId-FAILURE: Event completed in $duration ms\"\n        org.apache.log4j.Logger.getLogger(getClass).error(msg, e)\n      }\n    } finally {\n      if (client != null) {\n        try { client.close() } \n        catch { case _:Exception => () }\n      }\n    }\n  }\n  override def onJobEnd(jobEnd: SparkListenerJobEnd): Unit = logEvent(\"JobEnd: \" + jobEnd.jobId)\n  override def onJobStart(jobStart: SparkListenerJobStart): Unit = logEvent(\"JobStart: \" + jobStart.jobId)\n}\n\ndef showStudentSurvey():Unit = {\n  val html = renderStudentSurvey()\n  displayHTML(html);\n}\n\ndef renderStudentSurvey():String = {\n  import java.net.URLEncoder.encode\n  val utf8 = java.nio.charset.StandardCharsets.UTF_8.toString;\n  val username = encode(getUsername(), utf8)\n  val userhome = encode(getUserhome(), utf8)\n\n  val moduleName = encode(getModuleName(), utf8)\n  val lessonName = encode(getLessonName(), utf8)\n  val lessonNameUnencoded = getLessonName()\n  \n  val apiEndpoint = \"https://rqbr3jqop0.execute-api.us-west-2.amazonaws.com/prod\"\n\n  import scala.collection.Map\n  import org.json4s.DefaultFormats\n  import org.json4s.jackson.JsonMethods._\n  import org.json4s.jackson.Serialization.write\n  import org.json4s.JsonDSL._\n\n  implicit val formats: DefaultFormats = DefaultFormats\n\n  val feedbackUrl = s\"$apiEndpoint/feedback\";\n  \n  val html = \"\"\"\n  <html>\n  <head>\n    <script src=\"https://files.training.databricks.com/static/js/classroom-support.min.js\"></script>\n    <script>\n<!--    \n      window.setTimeout( // Defer until bootstrap has enough time to async load\n        () => { \n          $(\"#divComment\").css(\"display\", \"visible\");\n\n          // Emulate radio-button like feature for multiple_choice\n          $(\".multiple_choicex\").on(\"click\", (evt) => {\n                const container = $(evt.target).parent();\n                $(\".multiple_choicex\").removeClass(\"checked\"); \n                $(\".multiple_choicex\").removeClass(\"checkedRed\"); \n                $(\".multiple_choicex\").removeClass(\"checkedGreen\"); \n                container.addClass(\"checked\"); \n                if (container.hasClass(\"thumbsDown\")) { \n                    container.addClass(\"checkedRed\"); \n                } else { \n                    container.addClass(\"checkedGreen\"); \n                };\n                \n                // Send the like/dislike before the comment is shown so we at least capture that.\n                // In analysis, always take the latest feedback for a module (if they give a comment, it will resend the like/dislike)\n                var json = {\n                  moduleName:  \"GET_MODULE_NAME\", \n                  lessonName:  \"GET_LESSON_NAME\", \n                  orgId:       \"GET_ORG_ID\",\n                  username:    \"GET_USERNAME\",\n                  language:    \"scala\",\n                  notebookId:  \"GET_NOTEBOOK_ID\",\n                  sessionId:   \"GET_SESSION_ID\",\n                  survey: $(\".multiple_choicex.checked\").attr(\"value\"), \n                  comment: $(\"#taComment\").val() \n                };\n                \n                $(\"#vote-response\").html(\"Recording your vote...\");\n\n                $.ajax({\n                  type: \"PUT\", \n                  url: \"FEEDBACK_URL\", \n                  data: JSON.stringify(json),\n                  dataType: \"json\",\n                  processData: false\n                }).done(function() {\n                  $(\"#vote-response\").html(\"Thank you for your vote!<br/>Please feel free to share more if you would like to...\");\n                  $(\"#divComment\").show(\"fast\");\n                }).fail(function() {\n                  $(\"#vote-response\").html(\"There was an error submitting your vote\");\n                }); // End of .ajax chain\n          });\n\n\n           // Set click handler to do a PUT\n          $(\"#btnSubmit\").on(\"click\", (evt) => {\n              // Use .attr(\"value\") instead of .val() - this is not a proper input box\n              var json = {\n                moduleName:  \"GET_MODULE_NAME\", \n                lessonName:  \"GET_LESSON_NAME\", \n                orgId:       \"GET_ORG_ID\",\n                username:    \"GET_USERNAME\",\n                language:    \"scala\",\n                notebookId:  \"GET_NOTEBOOK_ID\",\n                sessionId:   \"GET_SESSION_ID\",\n                survey: $(\".multiple_choicex.checked\").attr(\"value\"), \n                comment: $(\"#taComment\").val() \n              };\n\n              $(\"#feedback-response\").html(\"Sending feedback...\");\n\n              $.ajax({\n                type: \"PUT\", \n                url: \"FEEDBACK_URL\", \n                data: JSON.stringify(json),\n                dataType: \"json\",\n                processData: false\n              }).done(function() {\n                  $(\"#feedback-response\").html(\"Thank you for your feedback!\");\n              }).fail(function() {\n                  $(\"#feedback-response\").html(\"There was an error submitting your feedback\");\n              }); // End of .ajax chain\n          });\n        }, 2000\n      );\n-->\n    </script>    \n    <style>\n.multiple_choicex > img {    \n    border: 5px solid white;\n    border-radius: 5px;\n}\n.multiple_choicex.choice1 > img:hover {    \n    border-color: green;\n    background-color: green;\n}\n.multiple_choicex.choice2 > img:hover {    \n    border-color: red;\n    background-color: red;\n}\n.multiple_choicex {\n    border: 0.5em solid white;\n    background-color: white;\n    border-radius: 5px;\n}\n.multiple_choicex.checkedGreen {\n    border-color: green;\n    background-color: green;\n}\n.multiple_choicex.checkedRed {\n    border-color: red;\n    background-color: red;\n}\n    </style>\n  </head>\n  <body>\n    <h2 style=\"font-size:28px; line-height:34.3px\"><img style=\"vertical-align:middle\" src=\"https://files.training.databricks.com/images/105/logo_spark_tiny.png\"/>What did you think?</h2>\n    <p>Please let us know if you liked this notebook, <b>LESSON_NAME_UNENCODED</b></p>\n    <div id=\"feedback\" style=\"clear:both;display:table;\">\n      <span class=\"multiple_choicex choice1 thumbsUp\" value=\"positive\"><img style=\"width:100px\" src=\"https://files.training.databricks.com/images/feedback/thumbs-up.png\"/></span>\n      <span class=\"multiple_choicex choice2 thumbsDown\" value=\"negative\"><img style=\"width:100px\" src=\"https://files.training.databricks.com/images/feedback/thumbs-down.png\"/></span>\n      <div id=\"vote-response\" style=\"color:green; margin:1em 0; font-weight:bold\">&nbsp;</div>\n      <table id=\"divComment\" style=\"display:none; border-collapse:collapse;\">\n        <tr>\n          <td style=\"padding:0\"><textarea id=\"taComment\" placeholder=\"How can we make this lesson better? (optional)\" style=\"height:4em;width:30em;display:block\"></textarea></td>\n          <td style=\"padding:0\"><button id=\"btnSubmit\" style=\"margin-left:1em\">Send</button></td>\n        </tr>\n      </table>\n    </div>\n    <div id=\"feedback-response\" style=\"color:green; margin-top:1em; font-weight:bold\">&nbsp;</div>\n  </body>\n  </html>\n  \"\"\"\n\n  return html.replaceAll(\"GET_MODULE_NAME\", getModuleName())\n             .replaceAll(\"GET_LESSON_NAME\", getLessonName())\n             .replaceAll(\"GET_ORG_ID\", getTag(\"orgId\", \"unknown\"))\n             .replaceAll(\"GET_USERNAME\", getUsername())\n             .replaceAll(\"GET_NOTEBOOK_ID\", getTag(\"notebookId\", \"unknown\"))\n             .replaceAll(\"GET_SESSION_ID\", getTag(\"sessionId\", \"unknown\"))\n             .replaceAll(\"LESSON_NAME_UNENCODED\", lessonNameUnencoded)\n             .replaceAll(\"FEEDBACK_URL\", feedbackUrl)\n\n}\n\n// ****************************************************************************\n// Facility for advertising functions, variables and databases to the student\n// ****************************************************************************\ndef allDone(advertisements: scala.collection.mutable.Map[String,(String,String,String)]):Unit = {\n  \n  var functions = scala.collection.mutable.Map[String,(String,String,String)]()\n  var variables = scala.collection.mutable.Map[String,(String,String,String)]()\n  var databases = scala.collection.mutable.Map[String,(String,String,String)]()\n  \n  for ( (key,value) <- advertisements) {\n    if (value._1 == \"f\" && spark.conf.get(s\"com.databricks.training.suppress.${key}\", null) != \"true\") {\n      functions += (key -> value)\n    }\n  }\n  \n  for ( (key,value) <- advertisements) {\n    if (value._1 == \"v\" && spark.conf.get(s\"com.databricks.training.suppress.${key}\", null) != \"true\") {\n      variables += (key -> value)\n    }\n  }\n  \n  for ( (key,value) <- advertisements) {\n    if (value._1 == \"d\" && spark.conf.get(s\"com.databricks.training.suppress.${key}\", null) != \"true\") {\n      databases += (key -> value)\n    }\n  }\n  \n  var html = \"\"\n  if (functions.size > 0) {\n    html += \"The following functions were defined for you:<ul style='margin-top:0'>\"\n    for ( (key,value) <- functions) {\n      html += s\"\"\"<li style=\"cursor:help\" onclick=\"document.getElementById('${key}').style.display='block'\">\n        <span style=\"color: green; font-weight:bold\">${key}</span>\n        <span style=\"font-weight:bold\">(</span>\n        <span style=\"color: green; font-weight:bold; font-style:italic\">${value._2}</span>\n        <span style=\"font-weight:bold\">)</span>\n        <div id=\"${key}\" style=\"display:none; margin:0.5em 0; border-left: 3px solid grey; padding-left: 0.5em\">${value._3}</div>\n        </li>\"\"\"\n    }\n    html += \"</ul>\"\n  }\n  if (variables.size > 0) {\n    html += \"The following variables were defined for you:<ul style='margin-top:0'>\"\n    for ( (key,value) <- variables) {\n      html += s\"\"\"<li style=\"cursor:help\" onclick=\"document.getElementById('${key}').style.display='block'\">\n        <span style=\"color: green; font-weight:bold\">${key}</span>: <span style=\"font-style:italic; font-weight:bold\">${value._2} </span>\n        <div id=\"${key}\" style=\"display:none; margin:0.5em 0; border-left: 3px solid grey; padding-left: 0.5em\">${value._3}</div>\n        </li>\"\"\"\n    }\n    html += \"</ul>\"\n  }\n  if (databases.size > 0) {\n    html += \"The following database were created for you:<ul style='margin-top:0'>\"\n    for ( (key,value) <- databases) {\n      html += s\"\"\"<li style=\"cursor:help\" onclick=\"document.getElementById('${key}').style.display='block'\">\n        Now using the database identified by <span style=\"color: green; font-weight:bold\">${key}</span>: \n        <div style=\"font-style:italic; font-weight:bold\">${value._2}</div>\n        <div id=\"${key}\" style=\"display:none; margin:0.5em 0; border-left: 3px solid grey; padding-left: 0.5em\">${value._3}</div>\n        </li>\"\"\"\n    }\n    html += \"</ul>\"\n  }\n  html += \"All done!\"\n  displayHTML(html)\n}\n\n// ****************************************************************************\n// Placeholder variables for coding challenge type specification\n// ****************************************************************************\nobject FILL_IN {\n  val VALUE = null\n  val ARRAY = Array(Row())\n  val SCHEMA = org.apache.spark.sql.types.StructType(List())\n  val ROW = Row()\n  val LONG: Long = 0\n  val INT: Int = 0\n  def DATAFRAME = spark.emptyDataFrame\n  def DATASET = spark.createDataset(Seq(\"\"))\n}\n\n// ****************************************************************************\n// Initialize the logger so that it can be used down-stream\n// ****************************************************************************\nval daLogger = new DatabricksAcademyLogger()\n// if (spark.conf.get(\"com.databricks.training.studentStatsService.registered\", null) != \"registered\") {\n//   sc.addSparkListener(daLogger)\n//   spark.conf.set(\"com.databricks.training.studentStatsService\", \"registered\")\n// }\ndaLogger.logEvent(\"Initialized\", \"Initialized the Scala DatabricksAcademyLogger\")\n\ndisplayHTML(\"Defining courseware-specific utility methods...\")\n", "commandTitle": "", "commandType": "auto", "commandVersion": 0, "commentThread": [], "commentsVisible": false, "customPlotOptions": {}, "datasetPreviewNameToCmdIdMap": {}, "diffDeletes": [], "diffInserts": [], "displayType": "table", "error": null, "errorSummary": null, "finishTime": 0, "globalVars": {}, "guid": "cc1d8e5a-efe9-4c82-a93b-3608978f6039", "height": "auto", "hideCommandCode": false, "hideCommandResult": false, "iPythonMetadata": null, "inputWidgets": {}, "latestUser": "", "latestUserId": null, "nuid": "8c5a44b8-9904-40d1-b719-8632702f6ca3", "origId": 0, "parentHierarchy": [], "pivotAggregation": null, "pivotColumns": null, "position": 1, "results": null, "showCommandTitle": false, "startTime": 0, "state": "finished", "streamStates": {}, "submitTime": 0, "subtype": "command", "version": "CommandV1", "width": "auto", "workflows": [], "xColumns": null, "yColumns": null}], "dashboards": [], "globalVars": {}, "guid": "a4141775-a6db-4718-a9de-0658902252fd", "iPythonMetadata": null, "inputWidgets": {}, "language": "scala", "name": "Class-Utility-Methods", "origId": 0, "version": "NotebookV1"}