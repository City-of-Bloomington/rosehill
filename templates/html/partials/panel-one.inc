<div id="panel-one">
<?php
	if (array_key_exists('panel-one',$this->blocks) && count($this->blocks['panel-one'])) {
		echo $this->includeBlocks('panel-one');
	}
	else {
?>
	<div id="aboutCemeteries">
		<h2>About the Cemeteries</h2>
		<ul>
			<li><h4><a href="https://bloomington.in.gov/locations/rose-hill-cemetery">
					Rose Hill Cemetery</h4></a><p>Driving Directions, Walking Tour, Notable Burials, History
				</p>
			</li>
			<li><h4><a href="https://bloomington.in.gov/locations/white-oak-cemetery">
					White Oak Cemetery</h4> </a><p> Driving Directions, Interment Information, History

			</li>
		</ul>
		<h2>Website Support</h2>
		<ul>
			<li><a href="https://bloomington.in.gov/cemeteries/web-services">
					Cemetery Data Download and Service
				</a>
			</li>
			<li><a href="https://bloomington.in.gov/departments/its/">Information & Technology Services</a></li>
			<li><a href="http://bloomington.in.gov">City of Bloomington</a></li>
		</ul>

	</div>
	<div id="cemeteryImages">
		<div><img alt="Rose Hill Cemetery" src="<?php echo BASE_URL."/images/rosehill.png"; ?>" /></div>
		<div><img alt="White Oak Cemetery" src="<?php echo BASE_URL."/images/whiteoakweb.jpg"; ?>" /></div>
	</div>
<?php
}
?>
</div>
