package io.task.loader.database;

import io.task.loader.BeanLoader;
import io.task.model.BeanModel;
import io.task.sql.RowMapper;
import io.task.sql.SqlWrapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static io.task.util.SqlConstant.*;


/**
 * <pre>
 * Created By : Ahmed Mobasher Khan
 * 
 * Purpose : 
 * 
 * Updated By : 
 * Updated Date : 
 * Comments :
 * </pre>
 */
public class BeanDefinitionDatabaseLoader implements BeanLoader<Map<String, BeanModel>>
{
	private SqlWrapper sqlWrapper = null;
//	private static String			countQuery	= "select count(*) from bean";


	public void setSqlWrapper(SqlWrapper sqlWrapper)
	{
		this.sqlWrapper = sqlWrapper;
	}


	@Override
	public Map<String, BeanModel> load()
	{

		return sqlWrapper.queryForMap(QRY_SEL_TBL_BEAN, new RowMapper<Map.Entry<String, BeanModel>>() {

			@Override
			public Map.Entry<String, BeanModel> mapRow(ResultSet rs, int rowNum)
			{
				try {
					final BeanModel bm = new BeanModel();
					bm.setId(rs.getString(COL_ID));
					bm.setClassName(rs.getString(COL_CLASS_NAME));
					bm.setType(rs.getString(COL_TYPE));
					
					return new Map.Entry<String, BeanModel>(){
	
						@Override
						public String getKey() {
							return bm.getId();
						}
	
						@Override
						public BeanModel getValue() {
							return bm;
						}
	
						@Override
						public BeanModel setValue(BeanModel value) {
							return bm;
						}
						
					};
				}
				catch(SQLException e)
				{
					throw new RuntimeException(e);
				}
			}
			
		});
	}


	public int getCount()
	{
		return 0;
//		JdbcTemplate jt = new JdbcTemplate(dataSource);
//		return jt.queryForObject(countQuery, Integer.class);
	}
}
