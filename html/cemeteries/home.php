<?php
/**
 * @copyright 2009-2010 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
$cemeteryList = new CemeteryList();
$cemeteryList->find();

$template = isset($_GET['format']) ? new Template('default',$_GET['format']) : new Template();
$template->blocks[] = new Block('cemeteries/cemeteryList.inc',array('cemeteryList'=>$cemeteryList));
echo $template->render();