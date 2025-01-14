<html>
<head>
<link href="PLUGINS_ROOT/org.robotframework.ide.eclipse.main.plugin.doc.user/help/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<a href="RED/../../../../help/index.html">RED - Robot Editor User Guide</a> &gt; <a href="RED/../../../../help/user_guide/user_guide.html">User guide</a> &gt; <a href="RED/../../../../help/user_guide/working_with_RED.html">Working with RED</a> &gt; 
<h2>Recognizing external libraries in RED</h2>
<dl class="warning">
<dt>Warning</dt>
<dd>As of now RED is only able to recognize libraries for which <i>library specification</i> file can be generated
   by <code>robot.libdoc</code> without arguments provided. This means that the library constructor
   cannot have required arguments (only default/varargs/kwargs).
   </dd>
</dl>
<p>In order to provide validation and keyword assistance of external libraries (any library not bundled with 
RobotFramework, but installed by pip or included in testcase by file path), external library <b>needs</b> to be 
included in <b> red.xml </b> file in the Referenced libraries part.</p>
<p>There are few ways to include library into red.xml.</p>
<h3>Library autodiscovery</h3>
<p>Main mechanism to include libraries' keywords in RED is done by autodiscovery mechanism. Mechanism works on two 
levels - during test edition and executed in Project Explorer on group of files and folders.<br/>
Every time when user edits test suite which has error markers on library declaration, autodiscovery is executed 
together with save action.<br/>
When there is a need to run autodiscovery on list of files, folders or whole project, this can be achieved by using 
right click menu in Project Explorer from Robot Framework option.</p>
<img src="images/autodiscovery_menu.png"/> <br/><br/>
<dl class="note">
<dt>Note</dt>
<dd>Library autodiscovering is not run on excluded project parts (see more under topic 
   <a href="../validation/scope.html">Limiting validation scope</a>).
   </dd>
</dl>
<h3>Quick Fix - shortcut CTRL+1</h3>
Lets focus on following example:
<br/><br/><img src="images/unknown_libs.png"/> <br/><br/>

In sample testcase, there are two libraries included. CalculatorLibrary.py is custom user local library placed in the 
same folder as testcase, Selenium2Library was installed by pip.<br/>
Both libraries are not recognized by RED as libraries names are underlined and error markers are placed next to line 
numbers. <br/>
Note the light bulb in error marker - it indicates that there is Quick Fix action available.  
<br/>Click on the library name in test editor, open right click menu and choose Quick Fix or use CTRL+1.
<br/><br/><img src="images/autodiscovery_quick_fix.png"/> <br/><br/>
After selecting Discover option, RED will search either PythonPath or library file path, if successful library will 
be added to Referenced libraries in red.xml 
<br/><br/><img src="images/reference_libs.png"/><br/>
<h3>Add library from Project Explorer</h3>
External Python libraries can be directly included to red.xml file by right clicking on file and using option:<br/>
<b><code>Robot Framework -> Add Library to red.xml.</code></b>
<h3>Add library from red.xml editor</h3>
External can be also added directly from red.xml editor:<br/><br/>
<img src="images/library_add.gif"/> <br/><br/>
<h3>Using libdoc file when external library is not present locally</h3>

In some scenarios, testware edit happens on different host than test runtime thus it is undesirable/unnecessary to 
install/import all libraries as on remote host. RobotFramework provides possibility to generate an xml file with list 
of keywords, this also provides agile test development where libraries are developed in parallel to test cases.<br/>
Libdoc xml file can be included instead of external library file by using red.xml editor.<br/><br/>
For more information about LibDoc please refer to <a class="external" href="http://robot-framework.readthedocs.io/en/latest/_modules/robot/libdoc.html?highlight=libdoc" target="_blank">http://robot-framework.readthedocs.io/en/latest/_modules/robot/libdoc.html?highlight=libdoc</a> <br/>
<h3>Library autodiscovering and libdoc generation preferences</h3>
<p>Autodiscovering and libdoc generation preferences can be configured at <code><a class="command" href="javascript:executeCommand('org.eclipse.ui.window.preferences(preferencePageId=org.robotframework.ide.eclipse.main.plugin.preferences.libraries)')">
Window -> Preferences -> Robot Framework -> Libraries</a></code></p>
<img src="images/libraries_preferences.png"/> <br/><br/>
<dl class="warning">
<dt>Warning</dt>
<dd>If the libraries you are going to debug use <code>Gevent</code> library then please select <b>Support Gevent 
       during autodiscovery</b> checkbox. This will make it possible for autodiscovery mechanism to support such libraries. 
       Without this you may experience hanging both when looking for libraries as well as when keyword source
       is being searched.
       </dd>
</dl>
<dl class="note">
<dt>Note</dt>
<dd>In some cases problems occur during libdoc generation via session server. It leads to a situation when the 
	   server hangs due to 
	   <a class="external" href="https://wiki.python.org/moin/GlobalInterpreterLock" target="_blank">Global Interpreter Lock</a>
	   between the server code and the library code.<br/> 
	   To avoid this problem since version 0.8.11 libdocs are generated in a separate process by default. Note that this solution
	   may affect the time of libdoc generation, i.e. slowing down the libraries import, especially with jython interpreter.
	   If you are not using problematic libraries you can disable the preference to make libdoc generation faster.
	   </dd>
</dl>
<h3>Reloading libraries after change</h3>

Whenever external library is changed (for instance new keyword is added), libdoc needs to be regenerated to provide 
changes on content assist and validation in RED.<br/>
Since version 0.6.3, RED can automatically detect library change and regenerate libdoc (it can be switch off/on in 
<a class="command" href="javascript:executeCommand('org.eclipse.ui.window.preferences(preferencePageId=org.robotframework.ide.eclipse.main.plugin.preferences.libraries)')">preferences</a>).<br/>
This can be also done manually by right clicking on library in Project Explorer and selecting <i>Reload action</i>
<p>Manual library reloading can be also useful for finding libdoc generation errors.<br/>
Whenever RED encounters libdoc generation problem, it will be shown as popup window from Python execution:</p>
<img src="images/libdoc_error.png"/> <br/><br/>
This indicates that some of the dependencies are missing on your local machine which may affect test suites execution.    
To verify you can try to execute libdoc in console:<br/>
<i>python -m robot.libdoc &lt;PATH_TO_LIBNAME&gt; list </i>
</body>
</html>