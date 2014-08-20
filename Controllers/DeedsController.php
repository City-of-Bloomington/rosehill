<?php
/**
 * @copyright 2014 City of Bloomington, Indiana
 * @license http://www.gnu.org/licenses/agpl.txt GNU/AGPL, see LICENSE.txt
 * @author Cliff Ingham <inghamn@bloomington.in.gov>
 */
namespace Application\Controllers;
use Application\Models\Deed;
use Application\Models\DeedsTable;
use Blossom\Classes\Controller;
use Blossom\Classes\Block;

class DeedsController extends Controller
{
    public function index()
    {
        $table = new DeedsTable();

        $knownFields = ['section_id','lot','cemetery_id','firstname','lastname','middleInitial'];
        if (count(array_intersect(array_keys($_GET), $knownFields))) {
            $list = $table->search($_GET, null, true);
        }
        else {
            $list = $table->find(null, null, true);
        }

        $page = !empty($_GET['page']) ? (int)$_GET['page'] : 1;
        $list->setCurrentPageNumber($page);
        $list->setItemCountPerPage(20);

        $this->template->blocks[] = new Block('deeds/findForm.inc');
        $this->template->blocks[] = new Block('deeds/list.inc',     ['deeds'    => $list]);
        $this->template->blocks[] = new Block('pageNavigation.inc', ['paginator'=> $list]);
    }

    public function update()
    {
        if (!empty($_REQUEST['deed_id'])) {
            try {
                $deed = new Deed($_REQUEST['deed_id']);
            }
            catch (\Exception $e) {
                $_SESSION['errorMessages'][] = $e;
            }
        }
        else {
            $deed = new Deed();
        }

        if (isset($_POST['deed_id'])) {
            try {
                $deed->handleUpdate($_POST);
                $deed->save();
                header('Location: '.BASE_URL.'/deeds');
                exit();
            }
            catch (\Exception $e) {
                $_SESSION['errorMessages'][] = $e;
            }
        }

        $this->template->blocks[] = new Block('deeds/updateForm.inc', ['deed'=>$deed]);
    }

    public function delete()
    {
        $return_url = !empty($_GET['return_url']) ? $_GET['return_url'] : BASE_URL.'/deeds';

        try {
            $deed = new Deed($_GET['deed_id']);
            $deed->delete();
        }
        catch (\Exception $e) {
            $_SESSION['errorMessages'][] = $e;
        }
        header("Location: $return_url");
        exit();
    }
}
