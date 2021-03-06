<%@page import="designer.model.vo.Designer"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String keyword = request.getAttribute("keyword") != null ? (String)request.getAttribute("keyword") : "";
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>관리자 페이지: 디자이너 관리</title>
    <style>
        .modal-overlay {
            display: none;
            position: fixed;
            width: 100%;
            height: 100%;

            background-color: gray;
            opacity: 0.5;
        }


        .designer-list,
        .review-list {
            width: 100%;
            border-collapse: collapse;
        }

        .designer-list>tbody>tr:hover,
        .review-list>tbody>tr:hover {
            background-color: rgb(235, 232, 232);
        }

        .designer-list th {
            border-bottom: 1px solid gray;
            height: 30px;
        }
    </style>
</head>

<body>
    <div class="admin-main-container">
        <header>
            <%@ include file="/WEB-INF/views/mypage/admin/common/header.jsp"%>
        </header>
        <section>
            <div class="admin-content">
                <form action="/mypageAdminDesigner" method="GET">
                    <%@ include file="/WEB-INF/views/mypage/admin/common/search-nav.jsp"%>
                </form>
                <form action="/adminDeleteDesigner">
                    <div class="designer-list-wrap">
                        <table class="designer-list">
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>번호</th>
                                    <th>아이디</th>
                                    <th>이름</th>
                                    <th>경력</th>
                                    <th>직급</th>
                                    <th>가입일</th>
                                    <th>기능</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:if test="${not empty list}">
                                    <c:forEach var="de" items="${list}">
                                        <tr>
                                            <th width="30"><input type="checkbox" name="designerId"
                                                    value="${de.designerId}">
                                            </th>
                                            <th>${de.designerNo}</th>
                                            <th>${de.designerId}</th>
                                            <th>${de.designerName}</th>
                                            <th>${de.designerYear}년</th>
                                            <th>${de.designerRank}</th>
                                            <th>${de.designerEnrolldate}</th>
                                            <th>
                                                <button class="btn del-one-btn">탈퇴</button>
                                            </th>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <th colspan="8">
                                        <button class="btn btn-allcheck" type="button">전체선택</button>
                                        <button class="btn del-btn">선택회원 탈퇴</button>
                                        <button class="btn" type="reset">전체 선택해제</button>
                                    </th>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                    <div class="page-nav">
                        <c:if test="${pageStart!=1}">
                            <a
                                href="mypageAdminDesigner?searchType=&keyword=${param.searchType}&keyword=${param.keyword}&reqPage=${pageStart-1}">이전</a>
                        </c:if>
                        <c:forEach var="i" begin="${pageStart}" end="${pageEnd}">
                            <a href="mypageAdminDesigner?searchType=${param.searchType}&keyword=${param.keyword}&reqPage=${i}"
                                style="${i==(not empty param.reqPage ? param.reqPage : 1) ? " color: black;" : ""
                                }">${i}</a>
                        </c:forEach>
                        <c:if test="${pageEnd<pageSize}">
                            <a
                                href="mypageAdminDesigner?searchType=${param.searchType}&keyword=${param.keyword}&reqPage=${pageEnd+1}">다음</a>
                        </c:if>
                    </div>
                </form>
            </div>
        </section>
    </div>
    <script>
        function setClickToTr() {
            $("tr").off('click');
            $("tr").on("click", function (e) {
                var chk = $(this).children().eq(0).children('input');
                if (chk.is(":checked")) {
                    chk.prop("checked", false);
                } else {
                    chk.prop("checked", true);
                }
            });
        }

        function setClickToReviewTr() {
            $(document).off("click", ".row-review");
            $(document).on("click", ".row-review", function (e) {
                var chk = $(this).children().eq(0).children('input');
                if (chk.is(":checked")) {
                    chk.prop("checked", false);
                } else {
                    chk.prop("checked", true);
                }
            });
        }

        function setClickToChk() {
            $(document).off("click", "[name=chk]");
            $(document).on("click", "[name=chk]", function (e) {
                if ($(this).is(":checked")) {
                    $(this).prop("checked", false);
                } else {
                    $(this).prop("checked", true);
                }
            });
        }

        function toggleCheckbox(checkbox) {
            if ($(checkbox).prop("checked") == true)
                $(checkbox).prop("checked", false);
            else if ($(checkbox).prop("checked") == false)
                $(checkbox).prop("checked", true);
        }

        function setClickToRemoveReviewBtn() {
            $(document).off("click", ".delete-review");
            $(document).on("click", ".delete-review", function (e) {
                if (confirm("정말 삭제하시겠습니까?")) {

                    let reviewNo = $(this).val();
                    $.ajax({
                        url: "/adminDeleteReview",
                        type: "post",
                        cache: false,
                        dataType: "text",
                        data: {
                            reviewNo: reviewNo
                        },
                        success: function (data) {
                            alert(data);
                            console.log(data);
                            //삭제후 리스트를 다시 불러옴.
                            $("#rvbtn").trigger("click");
                        }
                    })
                }
                e.stopPropagation();
            });
        }
        $(function () {
            $(".admin-nav a:eq(3)").css("background-color", "#F2C335");
            setClickToTr();
            setClickToChk()
            $(".modal-overlay").click(function (e) {
                $(".modal-overlay").css("display", "none");
                $(".review-container").css("display", "none");
            })
            $("#close-modal").click(function (e) {
                $(".modal-overlay").css("display", "none");
                $(".review-container").css("display", "none");
            })
            //한명 탈퇴 버튼 클릭 이벤트
            $(".del-one-btn").on("click", function (e) {
                $("input:checkbox[name=customerId]").prop("checked", false);
                if (!confirm("정말 탈퇴하시겠습니까?")) {
                    return false; //취소 눌렀을 시 submit 이벤트 발생 방지
                }
            })
            //선택회원 탈퇴 버튼 클릭 이벤트
            $(".del-btn").on("click", function (e) {
                if (!confirm("정말 탈퇴하시겠습니까?")) {
                    return false; //취소 눌렀을 시 submit 이벤트 발생 방지
                }
            })
            // 전체 선택 버튼 클릭 이벤트
            $(".btn-allcheck").on("click", function (e) {
                console.log($(".designer-list tbody th>input:checkbox"));
                toggleCheckbox($(".designer-list tbody th>input:checkbox"));

            })
        });
    </script>
</body>

</html>