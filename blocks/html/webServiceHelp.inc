<h1>Web Service API</h1>
<p>
	Cemetery provides read-only, RESTful style web services in XML and JSON.
	The idea is to provide all the information in output formats other than HTML.
	For example, adding format=XML to certain URLs will give you an XML version
	of the data.
</p>

<h2>Interment Data</h2>
<p><a href="<?php echo BASE_URL; ?>/interments"><?php echo BASE_URL; ?>/interments</a></p>

<p>
	Cemetery allows you to search for interments and receive the data as XML or JSON.
	The default is the HTML view, but adding a format parameter will give you data in the requested
	format. (assuming that format is one we support)
</p>
<p>
	An easy way to see it work, is to do a search as you normally would; then, add a 'format'
	parameter to the URL.
	Right now we support format=xml and format=json.
	We'll add more output formats as time goes on.
</p>

<p>The search parameters we currently support are:</p>
<ul><li>lastname</li>
	<li>firstname</li>
	<li>cemetery_id</li>
	<li>section_id</li>
</ul>

<p>
	Cemetery and Section require sending the ID number for the cemetery or section
	you're looking for.
	An easy way to discover the ID numbers is to view source on the search form,
	or look at the query strings of other searches.
</p>
<p>You can also get this information using the cemetery services.</p>

<h2>Cemetery Information</h2>
<p><a href="<?php echo BASE_URL; ?>/cemeteries"><?php echo BASE_URL; ?>/cemeteries</a></p>


<p>
	We also provide this data in XML and JSON format.
	A list of the cemeteries is at:
	<a href="<?php echo BASE_URL; ?>/cemeteries"><?php echo BASE_URL; ?>/cemeteries</a>.
</p>
<p>
	You can find out the sections for a cemetery on it's detail view:
	<?php echo BASE_URL; ?>/cemeteries/viewCemetery.php?cemetery_id=XX"
</p>