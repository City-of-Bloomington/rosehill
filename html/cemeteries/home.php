<?php
/**
 * @copyright 2009 City of Bloomington, Indiana
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */

$cemeteryList = new CemeteryList();
$cemeteryList->find();

$template = new Template();
$template->blocks[] = new Block('cemeteries/cemeteryList.inc',array('cemeteryList'=>$cemeteryList));
echo $template->render();