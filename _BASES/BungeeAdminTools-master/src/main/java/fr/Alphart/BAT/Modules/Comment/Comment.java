package fr.Alphart.BAT.Modules.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

import com.google.common.collect.Lists;

import fr.Alphart.BAT.BAT;
import fr.Alphart.BAT.I18n.I18n;
import fr.Alphart.BAT.Modules.BATCommand;
import fr.Alphart.BAT.Modules.IModule;
import fr.Alphart.BAT.Modules.ModuleConfiguration;
import fr.Alphart.BAT.Modules.Comment.CommentEntry.Type;
import fr.Alphart.BAT.Modules.Core.Core;
import fr.Alphart.BAT.Utils.Utils;
import fr.Alphart.BAT.database.DataSourceHandler;
import fr.Alphart.BAT.database.SQLQueries;

public class Comment implements IModule{
	private final String name = "comment";
	private CommentCommand commandHandler;
	private final CommentConfig config;

	public Comment(){
		config = new CommentConfig();
	}

	@Override
	public List<BATCommand> getCommands() {
		return commandHandler.getCmds();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getMainCommand() {
		return "comment";
	}

	@Override
	public ModuleConfiguration getConfig() {
		return config;
	}

	@Override
	public boolean load() {
		// Init table
		Statement statement = null;
		try (Connection conn = BAT.getConnection()) {
			statement = conn.createStatement();
			if (DataSourceHandler.isSQLite()) {
				for(final String commentsQuery : SQLQueries.Comments.SQLite.createTable){
					statement.executeUpdate(commentsQuery);
				}
			} else {
				statement.executeUpdate(SQLQueries.Comments.createTable);
			}
			statement.close();
		} catch (final SQLException e) {
			DataSourceHandler.handleException(e);
		} finally {
			DataSourceHandler.close(statement);
		}

		// Register commands
		commandHandler = new CommentCommand(this);
		commandHandler.loadCmds();
		
		return true;
	}

	@Override
	public boolean unload() {
		
		return true;
	}

	public class CommentConfig extends ModuleConfiguration {
		public CommentConfig() {
			init(name);
		}
		
		@net.cubespace.Yamler.Config.Comments({"Triggers list",
			"Trigger name:",
			"  pattern: reason which must be provided to trigger this",
			"  commands: list of commands that should be executed when it triggers, you can use {player} variable",
			"  triggerNumber: the number at which this triggers"})
		@Getter
		private Map<String, Trigger> triggers = new HashMap<String, Trigger>(){
			private static final long serialVersionUID = 1L;
		{
			put("example", new Trigger());
		}};
	}
	
	/**
	 * Get the notes relative to an entity
	 * @param entity | can be an ip or a player name
	 * @return
	 */
	public List<CommentEntry> getComments(final String entity){
		List<CommentEntry> notes = Lists.newArrayList();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (Connection conn = BAT.getConnection()) {
			statement = conn.prepareStatement(DataSourceHandler.isSQLite() 
					? SQLQueries.Comments.SQLite.getEntries
					: SQLQueries.Comments.getEntries);
			if(Utils.validIP(entity)){
				statement.setString(1, entity);
			}else{
				statement.setString(1, Core.getUUID(entity));
			}
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				final long date;
				if(DataSourceHandler.isSQLite()){
					date = resultSet.getLong("strftime('%s',date)") * 1000;
				}else{
					date = resultSet.getTimestamp("date").getTime();
				}
				notes.add(new CommentEntry(resultSet.getInt("id"), entity, resultSet.getString("note"), 
						resultSet.getString("staff"), CommentEntry.Type.valueOf(resultSet.getString("type")), 
						date));
			}
		} catch (final SQLException e) {
			DataSourceHandler.handleException(e);
		} finally {
			DataSourceHandler.close(statement, resultSet);
		}
		return notes;
	}
	
	public List<CommentEntry> getManagedComments(final String staff){
		List<CommentEntry> notes = Lists.newArrayList();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (Connection conn = BAT.getConnection()) {
			statement = conn.prepareStatement(DataSourceHandler.isSQLite() 
					? SQLQueries.Comments.SQLite.getManagedEntries
					: SQLQueries.Comments.getManagedEntries);
			statement.setString(1, staff);
			resultSet = statement.executeQuery();
			
			while(resultSet.next()){
				final long date;
				if(DataSourceHandler.isSQLite()){
					date = resultSet.getLong("strftime('%s',date)") * 1000;
				}else{
					date = resultSet.getTimestamp("date").getTime();
				}
				String entity = Core.getPlayerName(resultSet.getString("entity"));
				if(entity ==  null){
					entity = "UUID:" + resultSet.getString("entity");
				}
				notes.add(new CommentEntry(resultSet.getInt("id"), entity, resultSet.getString("note"), 
						staff, CommentEntry.Type.valueOf(resultSet.getString("type")), 
						date));
			}
		} catch (final SQLException e) {
			DataSourceHandler.handleException(e);
		} finally {
			DataSourceHandler.close(statement, resultSet);
		}
		return notes;
	}

	public void insertComment(final String entity, final String comment, final Type type, final String author){
		PreparedStatement statement = null;
		try (Connection conn = BAT.getConnection()) {
			statement = conn.prepareStatement(SQLQueries.Comments.insertEntry);
			statement.setString(1, (Utils.validIP(entity)) ? entity : Core.getUUID(entity));
			statement.setString(2, comment);
			statement.setString(3, type.name());
			statement.setString(4, author);
			statement.executeUpdate();
			statement.close();
			
			// Handle the trigger system
			for(final Trigger trigger : config.getTriggers().values()){
				if(trigger.getPattern().isEmpty() || comment.contains(trigger.getPattern())){
					statement = conn.prepareStatement((trigger.getPattern().isEmpty()) 
							? SQLQueries.Comments.simpleTriggerCheck
							: SQLQueries.Comments.patternTriggerCheck);
					statement.setString(1, Core.getUUID(entity));
					if(!trigger.getPattern().isEmpty()){
						statement.setString(2, '%' + trigger.getPattern() + '%');
					}
					
					final ResultSet rs = statement.executeQuery();
					if(rs.next()){
						int count = rs.getInt("COUNT(*)");
						if(trigger.getTriggerNumber() == count){
							trigger.onTrigger(entity, comment);
						}
					}
					
					rs.close();
					statement.close();
				}
			}
		} catch (final SQLException e) {
			DataSourceHandler.handleException(e);
		} finally {
			DataSourceHandler.close(statement);
		}
	}
	
	/**
	 * Clear all the comments and warning of an entity or the specified one
	 * @param entity
	 * @param commentID | use -1 to remove all the comments
	 * @return
	 */
	public String clearComments(final String entity, final int commentID){
		PreparedStatement statement = null;
		try (Connection conn = BAT.getConnection()) {
			if(commentID == -1){
				statement = conn.prepareStatement(SQLQueries.Comments.clearEntries);
				statement.setString(1, (Utils.validIP(entity)) ? entity : Core.getUUID(entity));
			}else{
				statement = conn.prepareStatement(SQLQueries.Comments.clearByID);
				statement.setString(1, (Utils.validIP(entity)) ? entity : Core.getUUID(entity));
				statement.setInt(2, commentID);
			}
			// Check if it was successfully deleted, will be used if tried to delete an specific id comment
			boolean deleted = statement.executeUpdate() > 0;
			
			if(commentID != -1){
				if(!deleted){
					throw new IllegalArgumentException(I18n._("noCommentIDFound", new String[] {entity}));
				}
				return I18n._("commentIDCleared", new String[] {String.valueOf(commentID), entity});
			}
			
			return I18n._("commentsCleared", new String[] {entity});
		} catch (final SQLException e) {
			return DataSourceHandler.handleException(e);
		} finally {
			DataSourceHandler.close(statement);
		}
	}
}
