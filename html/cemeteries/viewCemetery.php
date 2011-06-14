<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param $_GET cemetery_id
 * @param $_GET section (optional)
 */
try {
	if (!isset($_GET['cemetery_id']) || !$_GET['cemetery_id']) {
		throw new Exception('cemeteries/unknownCemetery');
	}
	$cemetery = new Cemetery($_GET['cemetery_id']);
}
catch (Exception $e) {
	$_SESSION['errorMessages'][] = $e;
	header('Location: '.BASE_URL);
	exit();
}


$template = isset($_GET['format'])
		? new Template('default',$_GET['format'])
		: $template = new Template();
$template->blocks[] = new Block('cemeteries/cemeteryInfo.inc',array('cemetery'=>$cemetery));
echo $template->render();
