package vn.geekup.data.model

import vn.geekup.domain.model.BaseModel

abstract class BaseRaw {
    abstract fun raw2Model(): BaseModel?
}
