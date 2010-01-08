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
			<li><a href="http://bloomington.in.gov/documents/viewDocument.php?document_id=1745">
					Rose Hill Cemetery
				</a>
			</li>
			<li><a href="http://bloomington.in.gov/documents/viewDocument.php?document_id=1747">
					White Oak Cemetery
				</a>
			</li>
			<li><a href="http://bloomington.in.gov/documents/viewDocument.php?document_id=1745">
					Notable Persons
				</a>
			</li>
			<li><a href="http://bloomington.in.gov/documents/viewDocument.php?document_id=1745">
					Rose Hill Historic Walking Tour
				</a>
			</li>
		</ul>
		<h2>Website Support</h2>
		<ul>
			<li><a href="http://bloomington.in.gov/documents/viewDocument.php?document_id=4578">
					Help
				</a>
			</li>
			<li><a href="http://bloomington.in.gov/documents/viewDocument.php?document_id=4577">
					Cemetery Data Download and Service
				</a>
			</li>
			<li><a href="<?php echo BASE_URL; ?>/services">Web Services</a></li>
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