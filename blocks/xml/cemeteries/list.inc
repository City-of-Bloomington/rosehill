<?php
/**
 * @copyright 2010-2014 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param Zend\Db\Result $this->cemeteries
 */
use Blossom\Classes\View;

echo '<cemeteries>';
foreach ($this->cemeteries as $cemetery) {
	$name         = View::escape($cemetery->getName());
	$url          = View::escape($cemetery->getURL());
	$googleMapUrl = View::escape($cemetery->getGoogleMapURL());
	echo "
	<cemetery id=\"{$cemetery->getId()}\">
		<name>$name</name>
		<url>$url</url>
		<googleMapUrl>$googleMapUrl</googleMapUrl>
	</cemetery>
	";
}
echo '</cemeteries>';
