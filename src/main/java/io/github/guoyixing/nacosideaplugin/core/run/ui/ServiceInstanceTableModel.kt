package io.github.guoyixing.nacosideaplugin.core.run.ui

import javax.swing.table.DefaultTableModel

/**
 * nacos服务实例表格模型
 *
 * @author 郭一行
 * @date 2024/11/29 15:59
 */
class ServiceInstanceTableModel(
    data: Array<Array<Any>>,
    columnNames: Array<Any>
) : DefaultTableModel() {

    init {
        setDataVector(data, columnNames)
    }


    override fun isCellEditable(row: Int, column: Int): Boolean {
        return column == 4
    }

}