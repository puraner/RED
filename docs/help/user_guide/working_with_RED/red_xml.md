<html>
<head>
<link href="PLUGINS_ROOT/org.robotframework.ide.eclipse.main.plugin.doc.user/help/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<a href="RED/../../../../help/index.html">RED - Robot Editor User Guide</a> &gt; <a href="RED/../../../../help/user_guide/user_guide.html">User guide</a> &gt; <a href="RED/../../../../help/user_guide/working_with_RED.html">Working with RED</a> &gt; 

<h1>General information about red.xml</h1>
<p>Red.xml is file which stores Project related settings, some of those controls editing phase (validation, code assistance etc.), other can influence RobotFramework execution.</p>
<p>It is always located in Project's root.</p>
<h2>Creating/recreating red.xml</h2>
<p>Red.xml is automatically created when Robot project is created by action from <b><code>File -> New -> Robot Project</code></b>.</p>
<p>In case of importing robot files to generic Eclipse project, right click on Project in Project Explorer and select <b><code>Robot Framework -> Add Robot nature</code></b> to create red.xml</p>
<img src="images/add_robot_nature.png"/> <br/><br/>
<p>In any case, red.xml shall be visible in Project Explorer in root folder.</p>
<h2>Red.xml parts</h2>
<p>Red.xml can be viewed using default editor by double click on file. It will load red.xml with graphical representation of file content. Alternatively, as this is xml file, it can be viewed and edited by any text editor.</p>
<p>Red.xml is divided in 4 groups,with respectful tabs at the bottom of the window: General, Libraries, Variables, Validation.</p>
<h3>General tab</h3>
<p>This section allows to set python interpreter regardless of interpreter setting in <a href="../launching/launch_prefs.html">Preferences</a>.</p>
<img src="images/red_xml_general_tab.png"/> <br/><br/>
<h3>Libraries tab</h3>
<p>This section holds everything related to Robot external libraries.</p>
<p><b>Libraries</b> - holds a list of discovered or manually added libraries. It is divided to <b>Referenced libraries</b> and <b>Remote locations</b>.</p>
<p>More info under topic <a href="libs.html">Recognizing external libraries in RED<a></a></a></p>
<p><b>Paths</b> - holds settings for user defined Python/ClassPath which are used for Libraries discovery and testcase execution, additionally relative paths relativity can be changed from Workspace to Project.</p>
<p>More info under topic <a href="custom_paths_relatve.html">Custom python/class paths and path relativeness</a> </p>
<img src="images/red_xml_libraries_tab.png"/> <br/><br/>
<h3>Variables tab</h3>
<p>This section holds everything related to Robot variables.</p>
<p><b>Variable mappings</b> - values can be assigned to variables, this is used to resolved parameterized paths. </p>
<p>More info under topic <a href="variable_mapping.html">Variable mapping - dealing with parameterized paths to libraries and resources</a> </p>
<p><b>Variable files</b> - this tab specify variable files which should be visible on global scope. Those global variable files are used during testcase edit and validation.</p>
<p>More info under topic <a href="variable_files.html">Variable Files - using files with variable accessible anywhere inside Project</a>.</p>
<img src="images/red_xml_variables_tab.png"/> <br/><br/>
<h3>Validation tab</h3>
<p>This section allows user to limit validation to selected folder or exclude selected folder from validation. To exclude folder in Project, right click on folder and choose <i>Exclude</i> from menu. Files can be also excluded by fixed size [KB]. </p>
<p>More info under topic <a href="../validation/scope.html">Limiting validation scope</a>.</p>
<img src="images/red_xml_validation_tab.png"/> <br/><br/>
</body>
</html>