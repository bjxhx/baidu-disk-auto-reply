package xyz.xhx.entity;

import java.io.Serializable;

/**
 * 文件实体类
 */
public class FileCommons implements Serializable {

    private Integer id;
    private Integer isdir;
    private String size;
    private Long fs_id;
    private String path;
    private String server_filename;
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    private boolean hasChildren;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIsdir() {
        return isdir;
    }

    public void setIsdir(Integer isdir) {
        this.isdir = isdir;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Long getFs_id() {
        return fs_id;
    }

    public void setFs_id(Long fs_id) {
        this.fs_id = fs_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getServer_filename() {
        return server_filename;
    }

    public void setServer_filename(String server_filename) {
        this.server_filename = server_filename;
    }
}
