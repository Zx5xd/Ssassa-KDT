<%@ page import="java.util.List" %>
<%@ page import="web.ssa.dto.categories.CategoryFieldsDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>Category Field 순서 조정</title>
<%--  <script>--%>
<%--    async function updateOrder() {--%>
<%--      const categoryId = parseInt(document.getElementById("categoryId").value);--%>
<%--      const childIdRaw = document.getElementById("childId").value;--%>
<%--      const childId = childIdRaw ? parseInt(childIdRaw) : null;--%>
<%--      const attributeKey = document.getElementById("attributeKey").value;--%>
<%--      const oldOrder = parseInt(document.getElementById("oldOrder").value);--%>
<%--      const newOrder = parseInt(document.getElementById("newOrder").value);--%>

<%--      const response = await fetch("/cat/set/displayOrder-static", {--%>
<%--        method: "POST",--%>
<%--        headers: {--%>
<%--          "Content-Type": "application/json"--%>
<%--        },--%>
<%--        body: JSON.stringify({--%>
<%--          categoryId,--%>
<%--          childId,--%>
<%--          attributeKey,--%>
<%--          oldOrder,--%>
<%--          newOrder--%>
<%--        })--%>
<%--      });--%>

<%--      if (response.ok) {--%>
<%--        alert("순서가 성공적으로 변경되었습니다!");--%>
<%--        location.reload();--%>
<%--      } else {--%>
<%--        const err = await response.text();--%>
<%--        alert("오류 발생: " + err);--%>
<%--      }--%>
<%--    }--%>
<%--  </script>--%>
  <%
    String dtoList = request.getParameter("dtoList")
  %>
</head>
<body>
<h1>Category Field 순서 조정</h1>
<label>Category ID: <input type="number" id="categoryId" required></label><br>
<label>Child Category ID (nullable): <input type="number" id="childId"></label><br>
<label>Attribute Key: <input type="text" id="attributeKey" required></label><br>
<label>Old Order: <input type="number" id="oldOrder" required></label><br>
<label>New Order: <input type="number" id="newOrder" required></label><br>
<button onclick="updateOrder()">순서 변경</button>
</body>
</html>
