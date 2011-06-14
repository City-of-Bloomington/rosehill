<?php
/**
 * @copyright 2010 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
$cemeteries = array();
foreach ($this->cemeteryList as $cemetery) {
	$name = addslashes($cemetery->getName());
	$url = addslashes($cemetery->getURL());
	$googleMapUrl = addslashes($cemetery->getGoogleMapURL());
	$cemeteries[] = "
	{	\"id\":\"{$cemetery->getId()}\",
		\"name\":\"$name\",
		\"url\":\"$url\",
		\"googleMapUrl\":\"$googleMapUrl\"
	}
	";
}
$cemeteries = implode(',',$cemeteries);
echo "[$cemeteries]";
