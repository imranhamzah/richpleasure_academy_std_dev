<?php
$lesson = array("lang"=>array(
    1=>array(
        "subject_id"      => 40,
        "subject_name"    => "Science",
        "chapter_id"      => 103,
        "chapter_no"      => 5,
        "chapter_name"    => "Function",
        "chapter_introduction" => "Bab ini berkenaan dengan Fotosintesis",
        "sub_chapter"     => array(
            "sub_chapter_id"    => "1.1",
            "sub_chapter_title" => "Introduction",
            "content" => array(
                array(
                    "note"=>"Cahaya + Air = Photosinthesis"
                ),
                array(
                    "note"=>"Bila..."
                )
            )
        ),
        "report_items"=>array()
    ),
    2=>array(
        "subject_id"      => 40,
        "subject_name"    => "Sains",
        "chapter_id"      => 103,
        "chapter_no"      => 5,
        "chapter_name"    => "Function",
        "chapter_introduction" => "Bab ini berkenaan dengan Fotosintesis",
        "sub_chapter"     => array(
            "sub_chapter_id"    => "1.1",
            "sub_chapter_title" => "Pengenalan",
            "content" => array(
                array(
                    "note"=>"Cahaya + Air = Photosinthesis"
                ),
                array(
                    "note"=>"Bila..."
                )
            )
        )
    ),
    "report_items"=>array()
),
               );

echo json_encode($lesson);

?>