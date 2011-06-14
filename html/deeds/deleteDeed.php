<?php
/**
 * @copyright 2010 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 * @param GET deed_id
 * @param GET return_url
 */
if (!userIsAllowed('Deeds')) {
	$_SESSION['errorMessages'][] = new Exception('noAccessAllowed');
	header('Location: '.BASE_URL);
	exit();
}

$deed = new Deed($_GET['deed_id']);
$deed->delete();

header('Location: '.$_GET['return_url']);
