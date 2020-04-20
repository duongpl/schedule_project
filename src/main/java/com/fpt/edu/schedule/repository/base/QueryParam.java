package com.fpt.edu.schedule.repository.base;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter

public class QueryParam<T> {

    private int page;
    private int limit;
    private Map<String, Object> criteria;
    private String sortField;
    private boolean ascending = true;
    private Map<String, Object> inCriteria;

    public QueryParam() {

    }
    public static class PagedResultSet<T> {

        private int page;
        private boolean running;

        public boolean isRunning() {
            return running;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        private int size;
        private int totalCount;
        private List<T> results;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<T> getResults() {
            return results;
        }

        public void setResults(List<T> results) {
            this.results = results;
        }
    }
}
