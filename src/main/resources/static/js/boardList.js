$(function () {
    getList();
});

const categoryId = $("#categoryId").val();
let grid

function getList() {
    const datasource = {
        api: {
            readData: {
                url: '/board/list/table',
                method: 'GET',
                initParams: {categoryId: categoryId, searchType: $("#searchType").val(), keyword: $("#keyword").val()}
            }
        }
    }
    grid = new tui.Grid({
        el: document.getElementById('recruitGrid'),
        scrollX: false,
        scrollY: false,

        data: datasource,

        columns: [
            {header: '번호', name: 'id', width: 'auto',align: 'center'},
            {header: '제목', name: 'title', width: 700, filter: {type: 'text'}},
            {header: '작성자', name: 'writer', align: 'center', filter: {type: 'text'}},
            {header: '카테고리', name: 'categoryName', align: 'center'},
            {header: '작성일', name: 'createdDate', align: 'center', width: 120},
            {header: '조회수', name: 'hit', align: 'center'}
        ],
        pageOptions: {
            //useClient: true,
            perPage: 5
        }
    });
    grid.on('click', ev => {
        const rowKey = ev.rowKey;
        const id = grid.getFormattedValue(rowKey, "id");

        if (ev.columnName === 'title' && id != null) {
            location.href = '/board/content?id=' + id;
        }
    });
}

/*검색 버튼*/
$("#searchButton").on('click', function () {
    if ($("#keyword").val() === "") {
        alert("검색어를 입력하세요");
    }else{
        grid.destroy();
        getList();
    }

});
