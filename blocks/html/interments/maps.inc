<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param Interment $this->interment
 */
echo "
<p class=\"back\">
	<a href=\"{$this->interment->getURL()}\">Back to Interment Details</a>
</p>
";

$highlighted_map = $this->interment->getSection()->getMap();
if ($highlighted_map) {
	echo "
	<h2>Entire Cemetery with Section Highlighted</h2>
	<div><img src=\"$highlighted_map\" /></div>
	";
}
else {
	echo "
	<h2>Entire Cemetery </h2>
	<div><img src=\"{$this->interment->getCemetery()->getMap()}\" /></div>
	";
}

$zoomed_map = $this->interment->getSection()->getMap('zoom');
if ($zoomed_map) {
	echo "
	<h2>Close-up of Section</h2>
	<div><img src=\"$zoomed_map\" /></div>
	";
}
else {
	echo "
	<h2>Close-up of Section</h2>
	<p>
		We do not have information for this burial or we do not have the section currently mapped.
	</p>
	";
}
