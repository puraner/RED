<html>
<head>
<link href="PLUGINS_ROOT/org.robotframework.ide.eclipse.main.plugin.doc.user/help/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<a href="RED/../../../../help/index.html">RED - Robot Editor User Guide</a> &gt; <a href="RED/../../../../help/user_guide/user_guide.html">User guide</a> &gt; <a href="RED/../../../../help/user_guide/validation.html">Validation</a> &gt; 

<h2>Running validation in command line</h2>
<p>RED can be run in headless mode (without GUI) to provide validation info of included projects. During validation,
settings are read from red.xml of each of the projects thus preserving links to referenced libraries, variable 
mappings or include/exclude folders. Only opened projects are validated.</p>
<p>Output of headless execution is report of validated projects and xml report in checkstyle format.
</p>
<p>Syntax:</p>
<div class="code">
eclipse 
-application org.robotframework.ide.eclipse.main.plugin.validation 
-data $WORKSPACE_PATH 
-nosplash 
-consoleLog 
--launcher.suppressErrors 
-import $PROJECT_PATHS 
-projects $PROJECT_NAMES 
-report $RAPORT_FILE
</div>
<ul>
<li><b>$PROJECT_PATHS</b> - list of paths to projects to be imported into workspace, separated by space</li>
<li><b>$PROJECT_NAMES</b> - list of projects to be validated from workspace, separated by space</li>
</ul>
</body>
</html>