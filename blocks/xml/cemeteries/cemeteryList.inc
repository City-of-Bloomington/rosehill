<cemeteries>
<?php
/**
 * @copyright 2010 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
foreach ($this->cemeteryList as $cemetery) {
	$name = View::escape($cemetery->getName());
	$url = View::escape($cemetery->getURL());
	$googleMapUrl = View::escape($cemetery->getGoogleMapURL());
	echo "
	<cemetery id=\"{$cemetery->getId()}\">
		<name>$name</name>
		<url>$url</url>
		<googleMapUrl>$googleMapUrl</googleMapUrl>
	</cemetery>
	";
}
?>
</cemeteries>